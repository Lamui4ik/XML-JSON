import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args)  {
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

    private static List parseXML(String s) {
        List<Employee> employees = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(s);
            NodeList employeeElements = document.getDocumentElement().getElementsByTagName("employee");
            for (int i = 0; i < employeeElements.getLength(); i++) {
                employees.add(getEmployee(employeeElements.item(i)));
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return employees;
    }

    private static Employee getEmployee(Node node) {
        Employee emp = new Employee();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            emp.setId(Long.parseLong(getTagValue("id", element)));
            emp.setFirstName(getTagValue("firstName", element));
            emp.setLastName(getTagValue("lastName", element));
            emp.setCountry(getTagValue("country", element));
            emp.setAge(Integer.parseInt(getTagValue("age", element)));
        }
        return emp;
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }

}