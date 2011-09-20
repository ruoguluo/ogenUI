package halogenui.processors.search;

import halogenui.models.Entry;
import halogenui.preferences.PreferenceConstants;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.widgets.Shell;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public abstract class AbstractSearchProcessor {

	protected XPathFactory xFactory = XPathFactory.newInstance();
	protected String searchValue;
	private String filePath = Platform.getPreferencesService().getString("halogenUI", PreferenceConstants.PATH, "", null);

	abstract protected XPathExpression getXPathExpression()  throws XPathExpressionException;

	public boolean calculateAvailablity(ExecutionEvent event){
		return true;
	}

	public ArrayList<Entry> search(){

		return doSearch();
	}

	public ArrayList<Entry> doSearch() {

		ArrayList<Entry> retVal = new ArrayList<Entry>();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(false);

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(filePath);

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

	public void showUsageWarning(Shell shell) {

	}

	public void setSearchKey(String s) {
		this.searchValue = s;
	}

}
