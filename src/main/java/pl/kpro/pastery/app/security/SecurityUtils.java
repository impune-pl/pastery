package pl.kpro.pastery.app.security;

import com.vaadin.flow.server.ServletHelper;
import com.vaadin.flow.shared.ApplicationConstants;

import javax.servlet.http.HttpServletRequest;

import java.util.stream.Stream;

/**
 * Checks if request is internal vaadin request.
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
public class SecurityUtils
{
    static boolean isVaadinInternalRequest(HttpServletRequest request)
    {
        final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
        return parameterValue != null && Stream.of(ServletHelper.RequestType.values())
                                               .anyMatch(r -> r.getIdentifier().equals(parameterValue));
    }
}
