package com.book.command.book;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.book.command.Command;
import com.book.db.BookDTO;

public class BookSearchCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		try{        
			HttpSession session = request.getSession();
			String key = "47EE5D5C730D424B9778B09F469F8CFE51152CEEB1A45131425CDB2D07C167E5";
			String query = (String)session.getAttribute("query");
			String queryType = (String)session.getAttribute("queryType");
			int start = 0;
			
			query = URLEncoder.encode(query, "utf-8");
			queryType = URLEncoder.encode(queryType, "utf-8");
			
			
			if(!queryType.equals("isbn")) start = (Integer)session.getAttribute("curPage");
			else start=1;
			
			String urlstr = "http://book.interpark.com/api/search.api?key="+key+
								"&queryType="+queryType+"&query="+query+"&start="+start+"&output=json";

			BufferedReader br = null;
			URL url = new URL(urlstr);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
			String result = "";
			String line;
			while((line = br.readLine()) != null) {
				result = result + line + "\n";
			}

			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject)parser.parse(result);
			JSONArray items = (JSONArray)obj.get("item");
			
			Long totalResults = 0L;
			totalResults = (long) obj.get("totalResults");
			int pageNum = 0;
			pageNum = (int) (totalResults%10 == 0 ? totalResults/10 : totalResults/10 + 1);
			
			ArrayList<BookDTO> list = new ArrayList<BookDTO>();
			for (int i=0;i< items.size();i++) {
				JSONObject info = (JSONObject) items.get(i);
				BookDTO dto = new BookDTO();
				
				dto.setTitle((String) info.get("title"));	           			// 제목
				dto.setDescription((String) info.get("description"));         	// 줄거리
				dto.setCoverSmallUrl((String) info.get("coverSmallUrl"));		// 표지 이미지(small)	
				dto.setCoverLargeUrl((String) info.get("coverLargeUrl"));    	// 표지 이미지
				dto.setAuthor((String) info.get("author"));       				// 작가
				dto.setPublisher((String) info.get("publisher"));     	  		// 출판사
				dto.setIsbn((String) info.get("isbn"));							// ISBN
				
				list.add(dto);
			}

			br.close();
			
			if(queryType.equals("isbn")) {
				request.setAttribute("dto", list.get(0));
			}
			else {
				request.setAttribute("bSearchList", list);
				request.setAttribute("query", (String)session.getAttribute("query"));
				request.setAttribute("queryType", queryType);
				request.setAttribute("curPage", start);
				request.setAttribute("pageNum", pageNum);
				
				session.removeAttribute("curPage");
			}
			
			session.removeAttribute("query");
			session.removeAttribute("queryType");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
