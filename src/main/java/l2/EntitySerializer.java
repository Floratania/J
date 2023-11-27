package l2;

/**
 * An interface representing a serializer and deserializer for entities, as well as methods for writing and reading from a file.
 *
 * @param <T> The type of the entity that the serializer works with.
 */
public interface EntitySerializer<T> {

    /**
     * Serializes an entity into a string.
     *
     * @param entity The entity to be serialized.
     * @return The serialized representation of the entity as a string.
     */
    String serialize(T entity);

    /**
     * Deserializes a string representation of an entity into an object.
     *
     * @param data The string representation of the entity.
     * @return The reconstructed entity object.
     */
    T deserialize(String data);

    /**
     * Writes an entity to a file.
     *
     * @param entity   The entity to be written to the file.
     * @param fileName The name of the file to write the entity to.
     */
    void writeToFile(T entity, String fileName);

    /**
     * Reads an entity from a file and returns it as an object.
     *
     * @param fileName The name of the file to read the entity from.
     * @return The reconstructed entity object from the file.
     */
    T readFromFile(String fileName);
}
