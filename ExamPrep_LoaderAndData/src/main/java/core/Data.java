package core;

import interfaces.Entity;
import interfaces.Repository;
import model.Invoice;
import model.StoreClient;
import model.User;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Data implements Repository {
    private static Set<String> ALLOWED_TYPES = Set.of(
            Invoice.class.getSimpleName(),
            StoreClient.class.getSimpleName(),
            User.class.getSimpleName()
    );

    TreeMap<Integer, Entity> idToEntityMap;

    public Data() {
        this(new TreeMap<>());
    }

    private Data(TreeMap<Integer, Entity> idToEntityMap) {
        this.idToEntityMap = idToEntityMap;
    }

    @Override
    public void add(Entity entity) {
        idToEntityMap.put(entity.getId(), entity);
    }

    @Override
    public Entity getById(int id) {
        return idToEntityMap.get(id);
    }

    @Override
    public List<Entity> getByParentId(int id) {
        return getAll().stream().filter(e -> e.getParentId() == id).collect(Collectors.toList());
//        Entity entityFound = idToEntityMap.get(id); // this is not the solution
//        return entityFound == null ? new ArrayList<>() : entityFound.getChildren();
    }

    @Override
    public List<Entity> getAll() {
        return new ArrayList<>(idToEntityMap.values());
    }

    @Override
    public Repository copy() {
        return new Data(this.idToEntityMap);
    }

    @Override
    public List<Entity> getAllByType(String type) {
        if (!ALLOWED_TYPES.contains(type)) {
            throw new IllegalArgumentException("Illegal type: " + type);
        }

        return new ArrayList<>(
                idToEntityMap.values().stream()
                        .filter(entity -> entity.getClass().getSimpleName().equals(type))
                        .collect(Collectors.toList()));
    }

    @Override
    public Entity peekMostRecent() {
        throwExceptionIfEmptyStructure();

        return idToEntityMap.lastEntry().getValue();
    }

    @Override
    public Entity pollMostRecent() {
        throwExceptionIfEmptyStructure();

        return idToEntityMap.pollLastEntry().getValue();
    }

    @Override
    public int size() {
        return idToEntityMap.size();
    }

    private void throwExceptionIfEmptyStructure() {
        if (idToEntityMap.isEmpty()) {
            throw new IllegalStateException("Operation on empty Data");
        }
    }
}
