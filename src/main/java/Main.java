import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        //String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.xml";
        List<Employee> list = parseXML(fileName);
        String json = listToJson(list);
        try {
            FileWriter fileWriter = new FileWriter("data.json");
            fileWriter.write(json);
            fileWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String listToJson(List<Employee> list) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        String json = gson.toJson(list, listType);
        return json;
    }

    private static List parseXML(String s) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(s));
        NodeList employeeElements = document.getDocumentElement().getElementsByTagName("employee");
        //Node root = document.getDocumentElement();
        // NodeList nodeList = root.getChildNodes();
        ArrayList<Employee> employees = new ArrayList<>();
        for (int i = 0; i < employeeElements.getLength(); i++) {
            Node employee = employeeElements.item(i);
            NamedNodeMap attributes = employee.getAttributes();
            employees.add(new Employee(Long.parseLong(attributes.getNamedItem("id").getNodeValue()),
                    attributes.getNamedItem("firstName").getNodeValue(),
                    attributes.getNamedItem("lastName").getNodeValue(),
                    attributes.getNamedItem("country").getNodeValue(),
                    Integer.parseInt(attributes.getNamedItem("age").getNodeValue())));
        }
        return employees;
    }
}

