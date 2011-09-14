package halogenui.processors.search;

import halogenui.preferences.PreferenceConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.eclipse.core.runtime.Platform;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class SearchPropertyValues {

	protected static XPathFactory xFactory = XPathFactory.newInstance();

	public static ArrayList<String> search(String xpath) throws Exception{
		String path = Platform.getPreferencesService().getString("halogenUI", PreferenceConstants.PATH, "", null);
		return search(xpath,path);
	}

	public static ArrayList<String> search(String xpath, String path) throws Exception{

		TreeSet<String> searchResult = new TreeSet<String>();
		ArrayList<String> retVal = new ArrayList<String>();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(false);

		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(path);

		XPathExpression expr = xFactory.newXPath().compile(xpath);
		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		for (int i=0;i<nodes.getLength();i++){
			searchResult.add(nodes.item(i).getNodeValue());
		}
		retVal.addAll(searchResult);
		Collections.sort(retVal);

		return retVal;
	}

	public static void main(String[] args){
		try {
			ArrayList<String> searchResult = search("area","halogenUI.xml");
			for (String string : searchResult){
				System.out.println(string);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
