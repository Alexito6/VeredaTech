import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class GestorInventarioDOM {

    public static Document cargarXML(String ruta) throws Exception{
        File xmlFile = new File(ruta);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xmlFile);
        doc.getDocumentElement().normalize();
        return doc;
    }
    public static void mostrarInventario(Document document){
        NodeList listaProductos=document.getElementsByTagName("producto");
        StringBuilder cadena=new StringBuilder();
        cadena.append("Inventario de La Vereda Tech: \n");

        for (int i = 0; i < listaProductos.getLength(); i++) {
            cadena.append("Producto º").append(i + 1).append(": ");

            Element producto= (Element) listaProductos.item(i);
            String idProducto=producto.getAttribute("id");
            String categoria=producto.getElementsByTagName("categoria").item(0).getTextContent();
            String nombre=producto.getElementsByTagName("nombre").item(0).getTextContent();
            String marca=producto.getElementsByTagName("marca").item(0).getTextContent();
            String precio=producto.getElementsByTagName("precio").item(0).getTextContent();
            String stock=producto.getElementsByTagName("stock").item(0).getTextContent();

            cadena.append("id: ").append(idProducto).append(", categoria: ").append(categoria).append(", nombre: ").append(nombre).append(", marca: ").append(marca).append(", precio: ").append(precio).append(", stock: ").append(stock).append("\n");

        }
        System.out.println(cadena);
    }
    public static void buscarProductosStockBajo(int limiteStock) throws Exception {
        Document document=cargarXML("inventario.xml");
        NodeList listaProductos=document.getElementsByTagName("producto");
        StringBuilder cadena=new StringBuilder();
        cadena.append("Inventario de stock inferior a ").append(limiteStock).append(" de La Vereda Tech: \n");

        for (int i = 0; i < listaProductos.getLength(); i++) {
            Element producto= (Element) listaProductos.item(i);
            String stock=producto.getElementsByTagName("stock").item(0).getTextContent();

            if (Integer.parseInt(stock)<limiteStock){
                String idProducto=producto.getAttribute("id");
                String categoria=producto.getElementsByTagName("categoria").item(0).getTextContent();
                String nombre=producto.getElementsByTagName("nombre").item(0).getTextContent();
                String marca=producto.getElementsByTagName("marca").item(0).getTextContent();
                String precio=producto.getElementsByTagName("precio").item(0).getTextContent();

                cadena.append("id: ").append(idProducto).append(", categoria: ").append(categoria).append(", nombre: ").append(nombre).append(", marca: ").append(marca).append(", precio: ").append(precio).append(", stock: ").append(stock).append("\n");
            }
        }
        System.out.println(cadena);
    }
    private static void actualizarStock(String idProducto,int stock) throws Exception {
        Document document=cargarXML("inventario.xml");
        List<String> idProductos=new ArrayList<>();

        NodeList listaProductos=document.getElementsByTagName("producto");
        for (int i = 0; i < listaProductos.getLength(); i++) {
            Element producto=(Element) listaProductos.item(i);
            String idProducto2=producto.getAttribute("id");
            idProductos.add(idProducto2);
        }
        if (!idProductos.contains(idProducto)){
            System.out.println("no se pudo actualizar el stock :(");
        }
        else {

        }
    }
}
