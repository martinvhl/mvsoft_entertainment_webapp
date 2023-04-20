package cz.mvsoft.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import cz.mvsoft.entity.entertainment.Film;
import cz.mvsoft.service.FilmsService;

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
        
        List<Film> films = new ArrayList<>();
        Film film1 = Film.builder().id(1).title("Avatar").year(2009).director("James Cameron").length(180).build();
        Film film2 = Film.builder().id(2).title("Vetřelec").year(1978).director("Ridley Scott").length(105).build();
        films.add(film1);
        films.add(film2);
        
        
        
        when(filmService.findById(80)).thenReturn(null);
    }

    @Test
    @DisplayName("testing getAllFilms")
    void testGetAllFilms() throws Exception {
    	List<Film> films = new ArrayList<>();
        Film film1 = Film.builder().id(1).title("Avatar").year(2009).director("James Cameron").length(180).build();
        Film film2 = Film.builder().id(2).title("Vetřelec").year(1978).director("Ridley Scott").length(105).build();
        films.add(film1);
        films.add(film2);
        Pageable pageable = PageRequest.of(0,4);
    	when(filmService.findAll(pageable)).thenReturn(films);
    	
        mockMvc.perform(get("/films/list"))
            .andExpect(status().isOk())
            .andExpect(view().name("films/films-list"))
            .andExpect(model().attribute("films", hasSize(2)))
            .andExpect(model().attribute("films",equalTo(films)));

        verify(filmService, times(1)).findAll(pageable);
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
        Film film1 = Film.builder().id(1).title("Avatar").year(2009).director("James Cameron").length(180).build();

    	when(filmService.findById(1)).thenReturn(film1);
    	
    	mockMvc.perform(get("/films/filmDetail/1"))
    		.andExpect(status().isOk())
    		.andExpect(view().name("films/film-detail"))
    		.andExpect(model().attributeExists("film"))
    		.andExpect(model().attribute("film",equalTo(film1)));
    	
    	verify(filmService, times(1)).findById(1);
        verifyNoMoreInteractions(filmService);
    }
    
    @Test
    @DisplayName("testing showFilmDetail - not found")
    void testShowFilmDetailNotFound() throws Exception {
    	when(filmService.findById(80)).thenReturn(null);
    	
    	mockMvc.perform(get("/films/filmDetail/80"))
    	.andExpect(status().isOk())
    	.andExpect(model().attributeDoesNotExist("film"))
    	.andExpect(view().name("films/film-not-found"));
    	
    	verify(filmService, times(1)).findById(80);
        verifyNoMoreInteractions(filmService);
    }
}
