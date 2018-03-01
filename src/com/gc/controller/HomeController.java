package com.gc.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/*
 * author: Nick Soule
 *
 */

@Controller
public class HomeController {

	@RequestMapping("/")
	public ModelAndView index(Model model) {

		String prodCenter = "";

		try {
			HttpClient http = HttpClientBuilder.create().build();
			HttpHost host = new HttpHost("deckofcardsapi.com", 443, "https");
			HttpGet getPage = new HttpGet("/api/deck/new/shuffle/?deck_count=1");
			HttpResponse resp = http.execute(host, getPage);
			String jsonString = EntityUtils.toString(resp.getEntity());
			JSONObject json = new JSONObject(jsonString);
			prodCenter = json.get("deck_id").toString();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ModelAndView("index", "cardDeck", prodCenter);
	}

	@RequestMapping("/welcome")
	public ModelAndView drawFive(@RequestParam("id") String deckID) {
		System.out.println(deckID);
		String text = "";
		try {
			HttpClient http = HttpClientBuilder.create().build();
			HttpHost host = new HttpHost("deckofcardsapi.com", 443, "https");
			HttpGet getPage = new HttpGet("/api/deck/" + deckID + "/draw/?count=5");
			HttpResponse resp = http.execute(host, getPage);
			String jsonString = EntityUtils.toString(resp.getEntity());
			JSONObject json = new JSONObject(jsonString);
			JSONArray arr = json.getJSONArray("cards");

			for (int i = 0; i < arr.length(); i++) {

				text += ("<img src=\"" + arr.getJSONObject(i).get("image") + "\" height=\"120\" width=\"90\"> <h4>"
						+ arr.getJSONObject(i).get("value") + " OF " + arr.getJSONObject(i).get("suit") + "</h4>");
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ModelAndView("welcome", "message", text);
	}

}