package ConcurrentHashTable;

public interface IExamSystem {

    void Add(long studentId, long courseId);
    void Remove(long studentId, long courseId);
    boolean Contains(long studentId, long courseId);
}
