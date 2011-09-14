package halogenui.processors.search;

import halogenui.model.Entry;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public abstract class AbstractSearchProcessor {

	protected XPathFactory xFactory = XPathFactory.newInstance();
	protected String filePath;
	protected String searchValue;

	abstract protected XPathExpression getXPathExpression()  throws XPathExpressionException;

	public void setFilePath(String filePath){
		this.filePath = filePath;
	}

	public ArrayList<Entry> search(String value){

		this.searchValue = value;
		return doSearch();
	}

	public ArrayList<Entry> doSearch() {

		ArrayList<Entry> retVal = new ArrayList<Entry>();

		if (filePath==null) {
			return retVal;
		}

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(false);

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(this.filePath);

			XPathExpression expr = getXPathExpression();
			Object result = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList nodes = (NodeList) result;
			for (int i=0;i<nodes.getLength();i++){

				Node node = nodes.item(i);
				Entry entry = new Entry();
				entry.setKey(node.getAttributes().item(0).getTextContent());
				entry.setDefaultValue(getChildNode(node,1).getTextContent());
				entry.setKeyLabel(getChildNode(node,3).getTextContent());
				entry.setModule(getChildNode(node,5).getTextContent());
				entry.setArea(getChildNode(node,7).getTextContent());
				retVal.add(entry);

			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return retVal;
	}

	private Node getChildNode(Node node, int index) {
		return node.getChildNodes().item(index);
	}

}
