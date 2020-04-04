package core;

import interfaces.Buffer;
import interfaces.Entity;
import model.BaseEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Loader implements Buffer {
    private List<Entity> entities;

    public Loader() {
        this.entities = new ArrayList<>();
    }

    @Override
    public void add(Entity entity) {
        entities.add(entity);
    }

    @Override
    public Entity extract(int id) {
        int entityIndex = getEntityIndexById(id);

        if (entityIndex == -1) {
            return null;
        }

        Entity extractedEntity = entities.get(entityIndex);
        entities.remove(entityIndex);
        return extractedEntity;
    }

    @Override
    public Entity find(Entity entity) {
        int entityIndex = getEntityIndexById(entity.getId());

        if (entityIndex == -1) {
            return null;
        }

        return entities.get(entityIndex);
    }

    @Override
    public boolean contains(Entity entity) {
        return getEntityIndexById(entity.getId()) != -1;
    }

    @Override
    public int entitiesCount() {
        return entities.size();
    }

    @Override
    public void replace(Entity oldEntity, Entity newEntity) {
        int entityIndex = getEntityIndexById(oldEntity.getId());

        throwExceptionIfElementNotFound(entityIndex);

        entities.set(entityIndex, newEntity);
    }

    @Override
    public void swap(Entity first, Entity second) {
        int firstEntityIndex = getEntityIndexById(first.getId());
        throwExceptionIfElementNotFound(firstEntityIndex);

        int secondEntityIndex = getEntityIndexById(second.getId());
        throwExceptionIfElementNotFound(secondEntityIndex);

        Entity temp = entities.get(firstEntityIndex);
        entities.set(firstEntityIndex, entities.get(secondEntityIndex));
        entities.set(secondEntityIndex, temp);
    }

    @Override
    public void clear() {
        entities.clear();
    }

    @Override
    public Entity[] toArray() {
        Entity[] result = new Entity[entities.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = entities.get(i);
        }
        return result;
    }

    @Override
    public List<Entity> retainAllFromTo(BaseEntity.Status lowerBound, BaseEntity.Status upperBound) {
        return entities.stream()
                .filter(e -> isBetweenBounds(e, lowerBound, upperBound))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Entity> getAll() {
        return entities.stream()
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void updateAll(BaseEntity.Status oldStatus, BaseEntity.Status newStatus) {
        for (Entity entity : entities) {
            if (entity.getStatus().equals(oldStatus)) {
                entity.setStatus(newStatus);
            }
        }
    }

    @Override
    public void removeSold() {
        entities = entities.stream()
                .filter(e -> e.getStatus() != BaseEntity.Status.SOLD)
                .collect(Collectors.toList());
    }

    @Override
    public Iterator<Entity> iterator() {
        return entities.iterator();
    }

    private int getEntityIndexById(int id) {
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).getId() == id) {
                return i;
            }
        }

        return -1;
    }

    private boolean isBetweenBounds(Entity entity, BaseEntity.Status lowerBound, BaseEntity.Status upperBound) {
        return lowerBound.ordinal() <= entity.getStatus().ordinal() && entity.getStatus().ordinal() <= upperBound.ordinal();
    }

    private void throwExceptionIfElementNotFound(int index) {
        if (index == -1) {
            throw new IllegalStateException("Entities not found");
        }
    }
}
