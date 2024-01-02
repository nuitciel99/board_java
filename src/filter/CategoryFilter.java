package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import dao.CategoryDao;

@WebFilter("/*")
public class CategoryFilter implements Filter{
	private CategoryDao categoryDao = new CategoryDao();
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
		request.setAttribute("categories", categoryDao.selectList());
		chain.doFilter(request, response);
	}

}
