package ru.pogorelov.Project3.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.pogorelov.Project3.dto.SensorDTO;
import ru.pogorelov.Project3.models.Measurement;
import ru.pogorelov.Project3.models.Sensor;
import ru.pogorelov.Project3.services.SensorService;
import ru.pogorelov.Project3.util.SensorValidator;

@RestController
@RequestMapping("/sensors")
public class SensorsController {

    private final SensorService sensorService;
    private final ModelMapper modelMapper;
    private final SensorValidator sensorValdator;

    @Autowired
    public SensorsController(SensorService sensorService, ModelMapper modelMapper, SensorValidator sensorValdator) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
        this.sensorValdator = sensorValdator;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult){
        Sensor sensorToAdd = convertToSensor(sensorDTO);

        sensorValdator.validate(sensorToAdd, bindingResult);

        if(bindingResult.hasErrors()) returnErrorsToClient(bindingResult);

        sensorService.register(sensorToAdd);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(Measurement e){
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(), System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Sensor convertToSensor (SensorDTO sensorDTO){
        return modelMapper.map(sensorDTO, Sensor.class);
    }
}
