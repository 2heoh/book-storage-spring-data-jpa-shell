package ru.agilix.bookstorage.service;

import org.springframework.stereotype.Service;
import ru.agilix.bookstorage.repository.GenreRepository;
import ru.agilix.bookstorage.ui.output.GenreOutputService;

@Service
public class CLIGenresService implements GenresService {
    private final GenreOutputService ui;
    private final GenreRepository repository;

    public CLIGenresService(GenreOutputService ui, GenreRepository repository) {
        this.ui = ui;
        this.repository = repository;
    }

    @Override
    public String showAllGenres() {
        return ui.showGenreList(repository.findAll());
    }
}
