package mytag;

import jakarta.servlet.ServletException;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;

public class MyTagHandler extends SimpleTagSupport {

    private String action;
    private String page;

    public void setAction(String action) {
        this.action = action;
    }

    public void setPage(String page) {
        this.page = page;
    }

    @Override
    public void doTag() throws JspException, IOException {

        PageContext pageContext = (PageContext) getJspContext();

        try {

            if ("include".equalsIgnoreCase(action)) {

                pageContext.include(page);

            } else if ("forward".equalsIgnoreCase(action)) {

                pageContext.forward(page);
            }

        } catch (ServletException e) {

            throw new JspException("Error while performing " + action + " action", e);
        }
    }
}