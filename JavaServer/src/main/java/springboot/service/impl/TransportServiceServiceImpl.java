package springboot.service.impl;

import javassist.NotFoundException;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import springboot.domein.TransportDienst;
import springboot.repository.TransportServiceRepository;
import springboot.service.TransportServiceService;

import java.util.List;


@Service
public class TransportServiceServiceImpl implements TransportServiceService {
    private final TransportServiceRepository transportServiceRepository;

    public TransportServiceServiceImpl(TransportServiceRepository transportServiceRepository) {
        super();
        this.transportServiceRepository = transportServiceRepository;
    }

    @Override
    public List<TransportDienst> getAllTransportServices(/*String token*/) throws IllegalAccessException {
        List<TransportDienst> transportDiensten = transportServiceRepository.findAll();
        return transportDiensten;
    }

    @Override
    public TransportDienst getTransportServiceById(Integer id, String token) throws IllegalAccessException {
        TransportDienst transportDienst = transportServiceRepository.getTransportServiceById(id);
        return transportDienst;
    }

    @Override
    public TransportDienst addTransportService(JSONObject jsonTransportService) {

        String name = (String) jsonTransportService.get("name");
        String emailAdresses = (String) jsonTransportService.get("emailAdresses");
        String phoneNumbers = (String) jsonTransportService.get("phoneNumbers");
        Boolean isActive = (Boolean) jsonTransportService.get("isActive");

        TransportDienst latestTransportDienst = transportServiceRepository.findTopByOrderByIdDesc();
        int newId = (latestTransportDienst == null) ? 1 : latestTransportDienst.getId() + 1;

        TransportDienst transportDienst = new TransportDienst(newId, name, emailAdresses, phoneNumbers, isActive);

        return transportServiceRepository.save(transportDienst);
    }

    @Override
    public void deleteTransportService(Integer id) {
        transportServiceRepository.deleteById(id);
    }

    @Override
    public TransportDienst editTransportService(Integer id, JSONObject jsonTransportService) throws NotFoundException, IllegalAccessException {
        TransportDienst existingTransportDienst = transportServiceRepository.findById(id);//.orElseThrow(() -> new NotFoundException("TransportDienst not found with id " + id));

        existingTransportDienst.setId((Integer) jsonTransportService.get("id"));
        existingTransportDienst.setName((String) jsonTransportService.get("name"));
        existingTransportDienst.setEmail((String) jsonTransportService.get("emailAdresses"));
        existingTransportDienst.setPhoneNumber((String) jsonTransportService.get("phoneNumbers"));
        existingTransportDienst.setActief((Boolean) jsonTransportService.get("isActive"));

        return transportServiceRepository.save(existingTransportDienst);
    }

}