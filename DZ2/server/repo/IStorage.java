package server.repo;

public interface IStorage {
    void saveData(String text);

    String getData();

}
