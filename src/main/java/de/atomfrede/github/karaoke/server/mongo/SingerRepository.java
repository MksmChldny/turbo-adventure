package de.atomfrede.github.karaoke.server.mongo;

import com.mongodb.DB;
import de.atomfrede.github.karaoke.server.entity.Singer;
import de.atomfrede.github.karaoke.server.repository.CrudRepository;

import org.bson.types.ObjectId;
import org.jongo.MongoCollection;

public class SingerRepository extends JongoManaged implements CrudRepository<Singer, String> {

    final String COLLECTION_NAME = "singer";
    final String ID_QUERY = "{_id:#}";
    final String IDS_QUERY = "{_id:{$in:#}}";

    MongoCollection collection;

    public SingerRepository(DB db) {
        super(db);
        collection = jongo.getCollection(COLLECTION_NAME);
    }

    @Override
    public long count() {
        return collection.count();
    }

    @Override
    public void delete(String s) {
        collection.remove(ID_QUERY, new ObjectId(s));
    }

    @Override
    public void delete(Iterable<? extends Singer> entities) {
        entities.forEach(this::delete);
    }

    @Override
    public void delete(Singer entity) {
        collection.remove(ID_QUERY, entity.id());
    }

    @Override
    public void deleteAll() {
        collection.drop();
    }

    @Override
    public boolean exists(String s) {
        return findOne(s) != null;
    }

    @Override
    public Iterable<Singer> findAll() {
        return collection.find().as(Singer.class);
    }

    @Override
    public Iterable<Singer> findAll(Iterable<String> strings) {
        return collection.find(IDS_QUERY, strings).as(Singer.class);
    }

    @Override
    public Singer findOne(String s) {
        return collection.findOne(ID_QUERY, s).as(Singer.class);
    }

    @Override
    public <S extends Singer> Iterable<S> save(Iterable<S> entities) {
        entities.forEach(this::save);
        return entities;
    }

    @Override
    public <S extends Singer> S save(S entity) {
        collection.save(entity);
        return entity;
    }

    @Override
    public <S extends Singer> Iterable<S> update(Iterable<S> entities) {
    	entities.forEach(this::update);
        return entities;
    }

    @Override
    public <S extends Singer> S update(S entity) {
    	collection.update(ID_QUERY, new ObjectId(entity.id())).with(entity);
        return entity;
    }

}