package halogenui.processors.search;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

public class SearchKeyByValueProcessor extends AbstractSearchProcessor {
	
	protected XPathExpression getXPathExpression() throws XPathExpressionException {

		XPath xpath = xFactory.newXPath();
		XPathExpression expr = xpath.compile("/globalUI/entry[default='"+searchValue+"']");
		
		return expr;
	}

}
