import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Document document;
        try {
            document = GestorInventarioDOM.cargarXML("inventario.xml");
            GestorInventarioDOM.buscarProductosStockBajo(20);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        GestorInventarioDOM.mostrarInventario(document);
    }
}
