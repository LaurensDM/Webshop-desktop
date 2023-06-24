package springboot.service;

import org.json.simple.JSONObject;
import springboot.domein.Box;

import java.util.List;

public interface BoxService {

    List<Box> getAllBoxes() throws IllegalAccessException;

    Box addBox(JSONObject jsonBox) throws IllegalAccessException;

    Box editBox(Integer id, JSONObject jsonBox) throws IllegalAccessException;

}
