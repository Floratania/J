package l2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * A JSON-based implementation of the EntitySerializer interface, designed to serialize and deserialize objects of a specified type using JSON format.
 *
 * @param <T> The type of entity that this serializer works with.
 */
public class JsonEntitySerializer<T> implements EntitySerializer<T> {
    private final ObjectMapper objectMapper;
    private final Class<T> entityClass;
    private static final Logger logger = LoggerFactory.getLogger(JsonEntitySerializer.class);

    /**
     * Constructs a new JsonEntitySerializer for a specified entity class.
     *
     * @param entityClass The class of the entity that this serializer will work with.
     */
    public JsonEntitySerializer(Class<T> entityClass) {
        this.entityClass = entityClass;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Serializes an entity into a JSON string.
     *
     * @param entity The entity to be serialized.
     * @return The JSON representation of the entity as a string.
     */
    @Override
    public String serialize(T entity) {
        try {
            return objectMapper.writeValueAsString(entity);
        } catch (Exception e) {
            logger.error("An error occurred:", e);
            return null;
        }
    }

    /**
     * Deserializes a JSON string into an object of the specified entity class.
     *
     * @param data The JSON string representation of the entity.
     * @return The reconstructed entity object.
     */
    @Override
    public T deserialize(String data) {
        try {
            return objectMapper.readValue(data, entityClass);
        } catch (Exception e) {
            logger.error("An error occurred:", e);
            return null;
        }
    }

    /**
     * Writes an entity to a JSON file.
     *
     * @param entity   The entity to be written to the file.
     * @param fileName The name of the JSON file to write the entity to.
     */
    @Override
    public void writeToFile(T entity, String fileName) {
        try {
            objectMapper.writeValue(new File(fileName), entity);
        } catch (Exception e) {
            logger.error("An error occurred:", e);
        }
    }

    /**
     * Reads an entity from a JSON file and returns it as an object.
     *
     * @param fileName The name of the JSON file to read the entity from.
     * @return The reconstructed entity object from the file.
     */
    @Override
    public T readFromFile(String fileName) {
        try {
            return objectMapper.readValue(new File(fileName), entityClass);
        } catch (Exception e) {
            logger.error("An error occurred:", e);
            return null;
        }
    }
}
