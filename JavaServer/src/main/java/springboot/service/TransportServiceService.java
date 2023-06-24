package springboot.service;

import javassist.NotFoundException;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import springboot.controller.TransportServiceController;
import springboot.domein.TransportDienst;

import java.util.List;

public interface TransportServiceService {

    List<TransportDienst> getAllTransportServices(
            /*String token*/
    ) throws IllegalAccessException;
    TransportDienst getTransportServiceById(
            Integer id,
            String token
    ) throws NotFoundException, IllegalAccessException;
    TransportDienst addTransportService(
            JSONObject jsonTransportService
    ) throws IllegalAccessException;
    void deleteTransportService(
            Integer id
    ) throws IllegalAccessException;
    TransportDienst editTransportService(
            Integer id,
            JSONObject jsonTransportService
    ) throws NotFoundException, IllegalAccessException;

}