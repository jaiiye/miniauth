package org.miniauth.web;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.miniauth.MiniAuthException;
import org.miniauth.oauth.credential.mapper.OAuthLocalTokenCredentialMapper;
import org.miniauth.web.oauth.OAuthProviderAuthHandler;

public class ProviderAuthHandlerTest
{

    private static final String consumerKey = "_consumer_key_1_";
    private static final String consumerSecret = "_consumer_secret_3_";
    private static final String accessToken = "_token_1_";
    private static final String tokenSecret = "_token_secret_3_";
    private OAuthLocalTokenCredentialMapper credentialMapper = null;
    private ProviderAuthHandler authHandler = null;
    private HttpServletRequest servletRequest = null;

    @Before
    public void setUp() throws Exception
    {
        credentialMapper = OAuthLocalTokenCredentialMapper.getInstance().setConsumerKey(consumerKey).setConsumerSecret(consumerSecret);
        credentialMapper.putTokenSecret("token1", "secret1");
        credentialMapper.putTokenSecret("token2", "secret2");
        credentialMapper.putTokenSecret(accessToken, tokenSecret);
        credentialMapper.setConsumerKey(consumerKey);
        credentialMapper.setConsumerSecret(consumerSecret);
        authHandler = new OAuthProviderAuthHandler(credentialMapper);
        
        // ????
        servletRequest = new HttpServletRequest ()
        {

            @Override
            public Object getAttribute(String name)
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Enumeration<String> getAttributeNames()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String getCharacterEncoding()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public void setCharacterEncoding(String env)
                    throws UnsupportedEncodingException
            {
                // TODO Auto-generated method stub
                
            }

            @Override
            public int getContentLength()
            {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public String getContentType()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public ServletInputStream getInputStream() throws IOException
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String getParameter(String name)
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Enumeration<String> getParameterNames()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String[] getParameterValues(String name)
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Map<String, String[]> getParameterMap()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String getProtocol()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String getScheme()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String getServerName()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public int getServerPort()
            {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public BufferedReader getReader() throws IOException
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String getRemoteAddr()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String getRemoteHost()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public void setAttribute(String name, Object o)
            {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void removeAttribute(String name)
            {
                // TODO Auto-generated method stub
                
            }

            @Override
            public Locale getLocale()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Enumeration<Locale> getLocales()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public boolean isSecure()
            {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public RequestDispatcher getRequestDispatcher(String path)
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String getRealPath(String path)
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public int getRemotePort()
            {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public String getLocalName()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String getLocalAddr()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public int getLocalPort()
            {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public ServletContext getServletContext()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public AsyncContext startAsync()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public AsyncContext startAsync(ServletRequest servletRequest,
                    ServletResponse servletResponse)
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public boolean isAsyncStarted()
            {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean isAsyncSupported()
            {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public AsyncContext getAsyncContext()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public DispatcherType getDispatcherType()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String getAuthType()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Cookie[] getCookies()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public long getDateHeader(String name)
            {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public String getHeader(String name)
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Enumeration<String> getHeaders(String name)
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Enumeration<String> getHeaderNames()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public int getIntHeader(String name)
            {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public String getMethod()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String getPathInfo()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String getPathTranslated()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String getContextPath()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String getQueryString()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String getRemoteUser()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public boolean isUserInRole(String role)
            {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public Principal getUserPrincipal()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String getRequestedSessionId()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String getRequestURI()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public StringBuffer getRequestURL()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String getServletPath()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public HttpSession getSession(boolean create)
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public HttpSession getSession()
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public boolean isRequestedSessionIdValid()
            {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromCookie()
            {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromURL()
            {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromUrl()
            {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean authenticate(HttpServletResponse response)
                    throws IOException, ServletException
            {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void login(String username, String password)
                    throws ServletException
            {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void logout() throws ServletException
            {
                // TODO Auto-generated method stub
                
            }

            @Override
            public Collection<Part> getParts() throws IOException,
                    IllegalStateException, ServletException
            {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Part getPart(String name) throws IOException,
                    IllegalStateException, ServletException
            {
                // TODO Auto-generated method stub
                return null;
            }
        
        };
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void testVerifyRequest()
    {
//        try {
//
//            // TBD:
//            // ....
//            
//            boolean verified = authHandler.verifyRequest(servletRequest);
//            System.out.println("verified = " + verified);
//            assertTrue(verified);
//
//            
//        } catch (MiniAuthException | IOException e) {
//            e.printStackTrace();
//        }
    }

}
