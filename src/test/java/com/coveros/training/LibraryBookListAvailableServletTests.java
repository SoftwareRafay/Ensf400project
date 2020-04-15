package com.coveros.training;

import com.coveros.training.domainobjects.Book;
import com.coveros.training.persistence.LibraryUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

public class LibraryBookListAvailableServletTests {

    public static final String A_BOOK = "a book";
    public static final Book DEFAULT_BOOK = new Book(1, A_BOOK);
    private HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    private HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    private LibraryBookListAvailableServlet libraryBookListAvailableServlet;
    private final RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);
    private LibraryUtils libraryUtils = Mockito.mock(LibraryUtils.class);


    @Before
    public void before() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        libraryBookListAvailableServlet = spy(new LibraryBookListAvailableServlet());
        libraryBookListAvailableServlet.libraryUtils = this.libraryUtils;
    }

    /**
     * If we don't pass a title or an id, we'll get a list of all books
     */
    @Test
    public void testListAvailableBooks() {
        when(request.getRequestDispatcher(ServletUtils.RESTFUL_RESULT_JSP)).thenReturn(requestDispatcher);

        // act
        libraryBookListAvailableServlet.doGet(request, response);

        // verify that the correct redirect was chosen.
        verify(libraryUtils).listAvailableBooks();
    }

    /**
     * If there aren't any books
     */
    @Test
    public void testSearchNoBooks() {
        when(request.getRequestDispatcher(ServletUtils.RESTFUL_RESULT_JSP)).thenReturn(requestDispatcher);
        when(request.getParameter("id")).thenReturn("");
        when(request.getParameter("title")).thenReturn("");

        // act
        libraryBookListAvailableServlet.doGet(request, response);

        // verify that the correct redirect was chosen.
        verify(request).setAttribute(LibraryBookListAvailableServlet.RESULT, "No books exist in the database");
    }


}
