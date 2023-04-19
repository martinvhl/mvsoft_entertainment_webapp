package cz.mvsoft.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInfo;

@Tag("entity")
public interface EntityTests {
	
	@BeforeEach()
	default void beforeEachEntityTest(TestInfo info) {
		System.out.println("Now running "+ info.getDisplayName());
	}
	
}
