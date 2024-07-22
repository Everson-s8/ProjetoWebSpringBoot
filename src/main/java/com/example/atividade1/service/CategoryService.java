package com.example.atividade1.service;

import com.example.atividade1.model.Category;
import com.example.atividade1.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @PostConstruct
    public void init() {
        // Log para iniciar a inicialização das categorias
        logger.info("Checking and updating categories as necessary...");

        // Método para adicionar ou atualizar categorias
        addOrUpdateCategory("Carros", "https://g1.globo.com/dynamo/carros/rss2.xml");
        addOrUpdateCategory("Ciência e Saúde", "https://g1.globo.com/dynamo/ciencia-e-saude/rss2.xml");
        addOrUpdateCategory("Concursos e Emprego", "https://g1.globo.com/dynamo/concursos-e-emprego/rss2.xml");
        addOrUpdateCategory("Economia", "https://g1.globo.com/dynamo/economia/rss2.xml");
        addOrUpdateCategory("Educação", "https://g1.globo.com/dynamo/educacao/rss2.xml");
        addOrUpdateCategory("Loterias", "https://g1.globo.com/dynamo/loterias/rss2.xml");
        addOrUpdateCategory("Mundo", "https://g1.globo.com/dynamo/mundo/rss2.xml");
        addOrUpdateCategory("Música", "https://g1.globo.com/dynamo/musica/rss2.xml");
        addOrUpdateCategory("Natureza", "https://g1.globo.com/dynamo/natureza/rss2.xml");
        addOrUpdateCategory("Planeta Bizarro", "https://g1.globo.com/dynamo/planeta-bizarro/rss2.xml");
        addOrUpdateCategory("Política", "https://g1.globo.com/dynamo/politica/mensalao/rss2.xml");
        addOrUpdateCategory("Pop & Arte", "https://g1.globo.com/dynamo/pop-arte/rss2.xml");
        addOrUpdateCategory("Tecnologia e Games", "https://g1.globo.com/dynamo/tecnologia/rss2.xml");
        addOrUpdateCategory("Turismo e Viagem", "https://g1.globo.com/dynamo/turismo-e-viagem/rss2.xml");
    }

    private void addOrUpdateCategory(String name, String url) {
        Optional<Category> optionalCategory = categoryRepository.findByName(name);
        Category category;
        if (!optionalCategory.isPresent()) {
            category = new Category(name, url);
            logger.info("Creating new category: {}", name);
        } else {
            category = optionalCategory.get();
            category.setRssUrl(url); // Atualiza a URL se necessário
            logger.info("Updating existing category: {}", name);
        }
        categoryRepository.save(category);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category saveOrUpdateCategory(Category category) {
        return categoryRepository.save(category);
    }
}
