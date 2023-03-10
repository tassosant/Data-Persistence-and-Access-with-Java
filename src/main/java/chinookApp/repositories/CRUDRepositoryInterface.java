package chinookApp.repositories;

import java.util.List;


    public interface CRUDRepositoryInterface<T, U> {
        List<T> findAll();
        T findById(U id);
        int insert(T object);
        int update(T object);
        int delete(T object);
        int deleteById(U id);



    }

