package pl.kpro.pastery.app.security;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
public class CustomRequestCache extends HttpSessionRequestCache
{
    @Override
    public void saveRequest(HttpServletRequest request, HttpServletResponse response)
    {
        if(!SecurityUtils.isVaadinInternalRequest(request))
            super.saveRequest(request, response);
    }
}
