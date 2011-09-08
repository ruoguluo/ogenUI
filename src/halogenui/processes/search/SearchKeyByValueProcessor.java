package halogenui.processes.search;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

public class SearchKeyByValueProcessor extends AbstractSearchProcessor {
	
	public static void main(String[] args){
		SearchKeyByValueProcessor searchKeyByValue = new SearchKeyByValueProcessor();
		searchKeyByValue.setFilePath("/Users/rluo/workspace_pluginDevelopment/HalogenUI/halogenUI.xml");
		searchKeyByValue.search("Secondary Managers");
		
	}

	protected XPathExpression getXPathExpression() throws XPathExpressionException {

		XPath xpath = xFactory.newXPath();
		XPathExpression expr = xpath.compile("/globalUI/entry[default='"+searchValue+"']");
		
		return expr;
	}

}
