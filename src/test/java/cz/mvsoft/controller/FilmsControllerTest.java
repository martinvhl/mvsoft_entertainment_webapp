package cz.mvsoft.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import cz.mvsoft.entity.entertainment.Film;
import cz.mvsoft.service.FilmsService;


//pro IT testy s anotací @WebMvcTest - endpointy zabezpečeny Spring Security -> potřeba importovat nastavení @Import(SecurityConfiguration.class) a pak připravovat testy s credentials
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
class FilmsControllerTest {
	
	private static final String ADD_FILM_FORM = "films/add-film-form";
	
	@Mock
    private FilmsService filmService;

    @InjectMocks
    private FilmsController filmsController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(filmsController).build();
    }

    @Test
    @DisplayName("testing getAllFilms")
    void testGetAllFilms() throws Exception {
    	List<Film> foundFilms = new ArrayList<>();
        Film film1 = Film.builder().id(1).title("Avatar").year(2009).director("James Cameron").length(180).build();
        Film film2 = Film.builder().id(2).title("Vetřelec").year(1978).director("Ridley Scott").length(105).build();
        foundFilms.add(film1);
        foundFilms.add(film2);
        
        Page<Film> page = new PageImpl<>(foundFilms);
        
    	when(filmService.findAll(PageRequest.of(0,8))).thenReturn(page);
    	
        mockMvc.perform(get("/films/list"))
            .andExpect(status().isOk())
            .andExpect(view().name("films/films-list"))
            .andExpect(model().attribute("films", hasSize(2)))
            .andExpect(model().attribute("films",equalTo(page.getContent())));

        verify(filmService, times(1)).findAll(PageRequest.of(0, 8));
        verifyNoMoreInteractions(filmService);
    }
    
    @Test
    @DisplayName("testing showAddFilmForm")
    void testShowAddFilmForm() throws Exception {
    	
    	mockMvc.perform(get("/films/showAddFilmForm"))
    		.andExpect(status().isOk())
    		.andExpect(model().attributeExists("film"))
    		.andExpect(view().name(ADD_FILM_FORM));
    }
    
    @Test
    @DisplayName("testing showFilmDetail - successfully found")
    void testShowFilmDetailSuccessful() throws Exception {
        List<Film> films = new ArrayList<>();
    	Film film1 = Film.builder().id(1).title("Avatar").year(2009).director("James Cameron").length(180).build();
    	films.add(film1);

        String username = "thorzard";
        
        Authentication auth = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    	
        Mockito.when(securityContext.getAuthentication()).thenReturn(auth);
        Mockito.when(auth.getName()).thenReturn(username);
        SecurityContextHolder.setContext(securityContext);
        
    	when(filmService.findById(1)).thenReturn(film1);
    	when(filmService.isFavourite(film1, username)).thenReturn(true);
    	
    	mockMvc.perform(get("/films/filmDetail/1"))
    		.andExpect(status().isOk())
    		.andExpect(view().name("films/film-detail"))
    		.andExpect(model().attributeExists("film"))
    		.andExpect(model().attribute("film",equalTo(film1)))
    		.andExpect(model().attributeExists("isFavourite"))
    		.andExpect(model().attribute("isFavourite", true));
    	
    	verify(filmService, times(1)).findById(1);
    	verify(filmService,times(1)).isFavourite(film1,username);
        verifyNoMoreInteractions(filmService);
    }
    
    @Test
    @DisplayName("testing showFilmDetail - not found")
    void testShowFilmDetailNotFound() throws Exception {
    	when(filmService.findById(80)).thenReturn(null);
    	
    	mockMvc.perform(get("/films/filmDetail/{id}",80))
    	.andExpect(status().isOk())
    	.andExpect(model().attributeDoesNotExist("film"))
    	.andExpect(view().name("films/film-not-found"));
    	
    	verify(filmService, times(1)).findById(80);
        verifyNoMoreInteractions(filmService);
    }
    
    @Test
    @DisplayName("testing showUpdateForm()")
    void testShowUpdateForm() throws Exception {
        Film film = Film.builder().id(1).title("Avatar").year(2009).director("James Cameron").length(180).build();

    	when(filmService.findById(1)).thenReturn(film);
    	
    	mockMvc.perform(get("/films/showUpdateForm/{id}",1))
    		.andExpect(status().isOk())
    		.andExpect(view().name("films/add-film-form"))
    		.andExpect(model().attribute("film", film));
    	
    	verify(filmService,times(1)).findById(1);
    	verifyNoMoreInteractions(filmService);
    }
    
    @Test
    @DisplayName("testing removeFilmsProcession")
    void testRemoveFilmProcessing() throws Exception {
    	mockMvc.perform(post("/films/removeFilm/{id}",1))
    		.andExpect(status().is3xxRedirection())
    		.andExpect(redirectedUrl("/films/list"));
    	
    		verify(filmService,times(1)).deleteById(1);
    		verifyNoMoreInteractions(filmService);
    }
    
    @Test
    @DisplayName("testing searchForFilm - null/blank name")
    void testSearchForFilmNullName() throws Exception {
    	mockMvc.perform(get("/films/filterFilms").param("filmName",""))
    		.andExpect(status().is3xxRedirection())
    		.andExpect(redirectedUrl("/films/list"));
    	
    	verifyNoInteractions(filmService);
    }
    
    @Test
    @DisplayName("testing searchForFilm - valid name")
    void testSearchForFilmValidName() throws Exception {
        String filmName = "ava";
    	Film film = Film.builder().id(1).title("Avatar").year(2009).director("James Cameron").length(180).build();
        List<Film> films = new ArrayList<>();
        films.add(film);
        
        when(filmService.filter(filmName)).thenReturn(films);
        
        mockMvc.perform(get("/films/filterFilms").param("filmName", filmName))
        	.andExpect(status().isOk())
        	.andExpect(view().name("films/films-list"))
        	.andExpect(model().attribute("films", films));
        
        verify(filmService,times(1)).filter(filmName);
        verifyNoMoreInteractions(filmService);
    }
    
    @Test
    @DisplayName("testing processFilmAddition - errors present")
    void testProcessFilmAdditionWithErrors() throws Exception {
    	Film film = Film.builder().id(1).title("").year(2009).director("James Cameron").description("Avatar movie description").filmType("scifi").length(180).build();
    	
        MockMultipartFile file = new MockMultipartFile("imageFile", "test.jpg", "image/jpeg", "test image content".getBytes());

        mockMvc.perform(multipart("/films/addFilm")
        		.file(file)
        		.flashAttr("film", film))
        .andExpect(status().isOk())
        .andExpect(model().attributeHasErrors("film"))
        .andExpect(model().attributeHasFieldErrors("film","title"))
        .andExpect(model().attribute("film", film))
        .andExpect(view().name(ADD_FILM_FORM));
        
        verifyNoInteractions(filmService);
    }
    
    @Test
    @DisplayName("testing processFilmAddition - film already exists")
    void testProcessFilmAdditionFilmExists() throws Exception {
    	Film film = Film.builder().title("Avatar").year(2009).director("James Cameron").description("Avatar movie description").filmType("scifi").length(180).build();
        Film foundFilm = Film.builder().id(13).title("Avatar").year(2009).director("James Cameron").description("Avatar movie description").filmType("scifi").length(180).build();
    	MockMultipartFile file = new MockMultipartFile("imageFile", "test.jpg", "image/jpeg", "test image content".getBytes());
        
    	when(filmService.searchByTitle(film.getTitle())).thenReturn(foundFilm);
    	
    	mockMvc.perform(multipart("/films/addFilm")
    			.file(file)
    			.flashAttr("film", film))
    	.andExpect(status().isOk())
    	.andExpect(model().attribute("film", new Film()))
    	.andExpect(model().attribute("filmExistsError", "This film is already in the database!"))
    	.andExpect(view().name(ADD_FILM_FORM));
    	
    	verify(filmService,times(1)).searchByTitle(film.getTitle());
    	verifyNoMoreInteractions(filmService);
    }
    
    @Test
    @DisplayName("testing processFilmAddition - film successfully added")
    void testProcessFilmAdditionSuccessful() throws Exception {
    	Film film = Film.builder().title("Avatar").year(2009).director("James Cameron").description("Avatar movie description").filmType("scifi").length(180).build();
    	MockMultipartFile file = new MockMultipartFile("imageFile", "test.jpg", "image/jpeg", "test image content".getBytes());
    	
    	when(filmService.searchByTitle(film.getTitle())).thenReturn(null);
    	when(filmService.save(film, file)).thenReturn(film);
    	
    	mockMvc.perform(multipart("/films/addFilm")
    			.file(file)
    			.flashAttr("film", film))
    	.andExpect(status().isOk())
    	.andExpect(view().name("films/film-addition-successful"));
    	
    	verify(filmService,times(1)).searchByTitle(film.getTitle());
    	verify(filmService,times(1)).save(film, file);
    	verifyNoMoreInteractions(filmService);
    }
    
    @Test
    @DisplayName("testing getFavouriteFilms success")
    void testGetFavouriteFilmsSuccessful() throws Exception {
    	Set<Film> films = new HashSet<>();
    	Film film1 = Film.builder().id(1).title("Avatar").year(2009).director("James Cameron").length(180).build();
        Film film2 = Film.builder().id(2).title("Vetřelec").year(1978).director("Ridley Scott").length(105).build();
    	films.add(film1);
    	films.add(film2);
    	String username = "thorzard";
    	
    	Page<Film> page = new PageImpl<>(List.copyOf(films));
    	
    	Authentication auth = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    	
        Mockito.when(securityContext.getAuthentication()).thenReturn(auth);
        Mockito.when(auth.getName()).thenReturn(username);
        SecurityContextHolder.setContext(securityContext);
    	when(filmService.getFavourites(username,PageRequest.of(0, 8))).thenReturn(Set.copyOf(page.getContent()));
    	
    	mockMvc.perform(get("/films/favourite/{username}",username))
        .andExpect(status().isOk())
        .andExpect(view().name("films/films-list"))
        .andExpect(model().attribute("films", hasSize(2)))
        .andExpect(model().attribute("films",equalTo(films)));
    	
    	verify(filmService, times(1)).getFavourites("thorzard", PageRequest.of(0, 8));
        verifyNoMoreInteractions(filmService);
    }
    
    @Test
    @DisplayName("testing getFavouriteFilms denied")
    void testGetFavouriteFilmsAccessDenied() throws Exception {
    	String loggedUsername = "thorzard";
    	String username = "gordon11";
    	    	
    	Authentication auth = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        
        Mockito.when(securityContext.getAuthentication()).thenReturn(auth);
        Mockito.when(auth.getName()).thenReturn(loggedUsername);
        SecurityContextHolder.setContext(securityContext);

        mockMvc.perform(get("/films/favourite/{username}",username))
        	.andExpect(status().isOk())
        	.andExpect(model().size(0))
        	.andExpect(view().name("access-denied"));
        
        verifyNoInteractions(filmService);
    }
}
