package halogenui.processes.search;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

public class SearchDefaultValueByKeyProcessor extends AbstractSearchProcessor {

	@Override
	protected XPathExpression getXPathExpression()
			throws XPathExpressionException {

		XPath xpath = xFactory.newXPath();
		XPathExpression expr = xpath.compile("/globalUI/entry[@key='" + searchValue + "']");
		
		return expr;
	}

}
