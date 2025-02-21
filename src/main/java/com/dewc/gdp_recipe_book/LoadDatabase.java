package com.dewc.gdp_recipe_book;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {
    
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(RecipeRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new Recipe(
                "BLT Sandwich",
                new String[] { "Bacon", "Lettuce", "Tomato" },
                "1. Toast Bread.\n2. Place bacon, lettuce and tomato on a slice of Toast.\n3. Put last slice of toast on top."
            )));

            log.info("Preloading " + repository.save(new Recipe(
                "Baked Beans",
                new String[] { "Can of Heinz Baked Beans" },
                "1. Open Microwave.\n2. Empty can of baked beans in bowl.\n3. Heat baked beans for 2 minutes."
            )));

            log.info("Preloading " + repository.save(new Recipe(
                "2 Minute Noodles",
                new String[] { "Cup of Noodles", "Egg" },
                "1. Boil water.\n2. Pour boiling water into cup of noodles.\n3.Crack your egg into the cup of noodles."
            )));
        };
    }
    
}
