package ru.alina.sparepartsmanager.part;
public class PartNotFoundException extends RuntimeException {
    public PartNotFoundException(Long id) {
        super("Запчасть с ID " + id + " не найдена");
    }
}