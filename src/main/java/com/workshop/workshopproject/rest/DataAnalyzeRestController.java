package com.workshop.workshopproject.rest;



import com.workshop.workshopproject.entity.OfferedWorkshop;
import com.workshop.workshopproject.entity.Registration;
import com.workshop.workshopproject.entity.Student;
import com.workshop.workshopproject.service.OfferedWorkshopService;
import com.workshop.workshopproject.service.StudentService;
import org.springframework.web.bind.annotation.*;



import java.util.*;




@RestController
@RequestMapping("api/")
public class DataAnalyzeRestController {
    private StudentService studentService;
    private OfferedWorkshopService offeredWorkshopService;

    public DataAnalyzeRestController(StudentService studentService, OfferedWorkshopService offeredWorkshopService) {

        this.studentService = studentService;
        this.offeredWorkshopService = offeredWorkshopService;
    }

    @GetMapping("analyze_workshop")
    public List<OfferedWorkshop> analyzeWorkshopPopularity(@RequestBody Map<String, Object> jsonBody){
        System.out.println("here");
        List<Student> students = studentService.findAll();
        OfferedWorkshop target = offeredWorkshopService.findById((int) jsonBody.get("offeredWorkshop_id"));
        
        HashMap<Student,List<OfferedWorkshop>> scattering = new HashMap<>();
        HashMap<OfferedWorkshop,Integer> scattering1 = new HashMap<>();
        HashMap<List<OfferedWorkshop>,Integer> scattering2 = new HashMap<>();
        HashMap<List<OfferedWorkshop>,Integer> scattering3 = new HashMap<>();


        for (Student student: students) {
            List<OfferedWorkshop> offeredWorkshops = new ArrayList<>();
            for (Registration r : student.getRegistrations()) {
                OfferedWorkshop offeredWorkshop = r.getEnrolledWorkshop().getStudentGroup().getOfferedWorkshop();
                offeredWorkshops.add(offeredWorkshop);
            }
            scattering.put(student,offeredWorkshops);
        }

        int minimum = students.size()*20 / 100;
        // one member
        for (Map.Entry<Student,List<OfferedWorkshop>> entry:scattering.entrySet()) {
            for (OfferedWorkshop o:entry.getValue()) {
                if (scattering1.containsKey(o)){
                    scattering1.replace(o,scattering1.get(o)+1);
                }
                else scattering1.put(o,1);
            }
        }
        for (Map.Entry<OfferedWorkshop, Integer> entry:scattering1.entrySet()) {
            if (entry.getValue() < minimum){
                scattering1.remove(entry.getKey());
            }
        }

        // two member

        for (Map.Entry<Student,List<OfferedWorkshop>> entry:scattering.entrySet()) {
            for (OfferedWorkshop o1:entry.getValue()) {
                for (OfferedWorkshop o2:entry.getValue()) {
                    List<OfferedWorkshop> offeredWorkshops = new ArrayList<>();
                    offeredWorkshops.add(o1);
                    offeredWorkshops.add(o2);
                    if (entry.getValue().contains(o1) && entry.getValue().contains(o2) && scattering2.containsKey(offeredWorkshops)){
                        scattering2.replace(offeredWorkshops,scattering2.get(offeredWorkshops)+1);
                    }
                    else scattering2.put(offeredWorkshops,1);
                }
            }
        }

        for (Map.Entry<List<OfferedWorkshop>, Integer> entry:scattering2.entrySet()) {
            if (entry.getValue() < minimum){
                scattering2.remove(entry.getKey());
            }
        }

        // three member

        for (Map.Entry<Student,List<OfferedWorkshop>> entry:scattering.entrySet()) {
            for (OfferedWorkshop o1:entry.getValue()) {
                for (OfferedWorkshop o2:entry.getValue()) {
                    for (OfferedWorkshop o3:entry.getValue()) {
                        List<OfferedWorkshop> offeredWorkshops = new ArrayList<>();
                        offeredWorkshops.add(o1);
                        offeredWorkshops.add(o2);
                        offeredWorkshops.add(o3);
                        if (entry.getValue().contains(o1) && entry.getValue().contains(o2) && entry.getValue().contains(o3) && scattering2.containsKey(offeredWorkshops)){
                            scattering3.replace(offeredWorkshops,scattering3.get(offeredWorkshops)+1);
                        }
                        else scattering3.put(offeredWorkshops,1);
                    }

                }
            }
        }

        for (Map.Entry<List<OfferedWorkshop>, Integer> entry:scattering3.entrySet()) {
            if (entry.getValue() < minimum){
                scattering3.remove(entry.getKey());
            }
        }
        int max = 2; // maximum set of results

        int targetWorkshopCount = scattering1.get(target);

        List<OfferedWorkshop> result = new ArrayList<>();
        //sort
        scattering3 = (HashMap<List<OfferedWorkshop>, Integer>) sortByValue(scattering3);
        scattering2 = (HashMap<List<OfferedWorkshop>, Integer>) sortByValue(scattering2);
        //
        if (scattering3.size() >= 2){
            for (Map.Entry<List<OfferedWorkshop>, Integer> entry:scattering3.entrySet()) {
                if (max != 0){
                    result.addAll(entry.getKey());
                    max--;
                }
                if (max < 0){
                    break;
                }
            }
        }

        if (max > 0){
            for (Map.Entry<List<OfferedWorkshop>, Integer> entry:scattering2.entrySet()) {
                if (max != 0){
                    result.addAll(entry.getKey());
                    max--;
                }
                if (max < 0){
                    break;
                }
            }
        }

        return result;
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(HashMap<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

}
