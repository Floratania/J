package l2;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * An XML-based implementation of the EntitySerializer interface, designed to serialize and deserialize objects of a specified type using XML format.
 *
 * @param <T> The type of entity that this serializer works with.
 */
public class XmlEntitySerializer<T> implements EntitySerializer<T> {
    private final Class<T> entityClass;

    private static final Logger logger = LoggerFactory.getLogger(XmlEntitySerializer.class);

    /**
     * Constructs a new XmlEntitySerializer for a specified entity class.
     *
     * @param entityClass The class of the entity that this serializer will work with.
     */
    public XmlEntitySerializer(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Serializes an entity into an XML string.
     *
     * @param entity The entity to be serialized.
     * @return The XML representation of the entity as a string.
     */
    @Override
    public String serialize(T entity) {
        try {
            JAXBContext context = JAXBContext.newInstance(entityClass);
            Marshaller marshaller = context.createMarshaller();
            StringWriter writer = new StringWriter();
            marshaller.marshal(entity, writer);
            return writer.toString();
        } catch (JAXBException e) {
            logger.error("An error occurred:", e);
            return null;
        }
    }

    /**
     * Deserializes an XML string into an object of the specified entity class.
     *
     * @param data The XML string representation of the entity.
     * @return The reconstructed entity object.
     */
    @Override
    public T deserialize(String data) {
        try {
            JAXBContext context = JAXBContext.newInstance(entityClass);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader reader = new StringReader(data);
            return entityClass.cast(unmarshaller.unmarshal(reader));
        } catch (JAXBException e) {
            logger.error("An error occurred:", e);
            return null;
        }
    }

    /**
     * Writes an entity to an XML file.
     *
     * @param entity   The entity to be written to the file.
     * @param fileName The name of the XML file to write the entity to.
     */
    @Override
    public void writeToFile(T entity, String fileName) {
        try {
            JAXBContext context = JAXBContext.newInstance(entityClass);
            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(entity, new File(fileName));
        } catch (JAXBException e) {
            logger.error("An error occurred:", e);
        }
    }

    /**
     * Reads an entity from an XML file and returns it as an object.
     *
     * @param fileName The name of the XML file to read the entity from.
     * @return The reconstructed entity object from the file.
     */
    @Override
    public T readFromFile(String fileName) {
        try {
            JAXBContext context = JAXBContext.newInstance(entityClass);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return entityClass.cast(unmarshaller.unmarshal(new File(fileName)));
        } catch (JAXBException e) {
            logger.error("An error occurred:", e);
            return null;
        }
    }
}
