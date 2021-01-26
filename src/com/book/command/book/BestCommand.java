package com.book.command.book;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.book.command.Command;
import com.book.db.BookDTO;

public class BestCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		try{    
			HttpSession session = request.getSession();
			BufferedReader br = null;
			String key = "47EE5D5C730D424B9778B09F469F8CFE51152CEEB1A45131425CDB2D07C167E5";
			int categoryId = 0;
			
			categoryId = (int)session.getAttribute("categoryId");
			
			String urlstr = "http://book.interpark.com/api/bestSeller.api?key="+key+"&categoryId="+categoryId+"&output=json";

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
				dto.setCustomerReviewRank(info.get("customerReviewRank").toString());	// 평점
				dto.setRank((long) info.get("rank"));							// 순위
				dto.setIsbn((String)info.get("isbn"));							// ISBN
				dto.setLink((String)info.get("link"));							// 책 상세 url
				
				list.add(dto);
				
			}

			br.close();

			request.setAttribute("best", list);
			request.setAttribute("pageNum", pageNum);
			request.setAttribute("pageType", "best");
			
			
			if(session.getAttribute("curPage") != null) {
				request.setAttribute("curPage", (int)session.getAttribute("curPage"));
				session.removeAttribute("curPage");	
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}


