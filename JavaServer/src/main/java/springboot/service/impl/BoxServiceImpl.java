package springboot.service.impl;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import springboot.domein.Box;
import springboot.repository.BoxRepository;
import springboot.service.BoxService;

import java.util.List;

@Service
public class BoxServiceImpl implements BoxService {

    private final BoxRepository boxRepository;

    public BoxServiceImpl(BoxRepository boxRepository) {
        super();
        this.boxRepository = boxRepository;
    }

    @Override
    public List<Box> getAllBoxes() throws IllegalAccessException {
        List<Box> boxes = boxRepository.findAll();
        return boxes;
    }

    @Override
    public Box addBox(JSONObject jsonBox){

        String name = (String) jsonBox.get("name");
        String type = (String) jsonBox.get("type");
        double width = (double) jsonBox.get("width");
        double length = (double) jsonBox.get("length");
        double height = (double) jsonBox.get("height");
        Double price = (Double) jsonBox.get("price");
        Boolean isActive = (Boolean) jsonBox.get("isActive");

        Box latestBox = boxRepository.findTopByOrderByIdDesc();
        int newId = (latestBox == null) ? 1 : latestBox.getId() + 1;

        Box box = new Box(newId, name, type, width, length, height, price, isActive);
        return boxRepository.save(box);
    }

    @Override
    public Box editBox(Integer id, JSONObject jsonBox) throws IllegalAccessException {
        Box existingBox = boxRepository.findById(id);//.orElseThrow(() -> new NotFoundException("Box not found with id " + id));

        existingBox.setId((Integer) jsonBox.get("id"));
        existingBox.setName((String) jsonBox.get("name"));
        existingBox.setType((String) jsonBox.get("type"));
//        existingBox.setDimensions((String) jsonBox.get("dimensions"));
        existingBox.setWidth((Double) jsonBox.get("width"));
        existingBox.setLength((Double) jsonBox.get("length"));
        existingBox.setHeight((Double) jsonBox.get("height"));
        existingBox.setPrice((Double) jsonBox.get("price"));
        existingBox.setIsActive((Boolean) jsonBox.get("isActive"));

        return boxRepository.save(existingBox);
    }

}
