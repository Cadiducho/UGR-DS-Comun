package p3.farmacia.modelo;

@FunctionalInterface
public interface ApiCallback<T> {

    void then(T res);

}