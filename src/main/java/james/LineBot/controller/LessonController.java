package james.LineBot.controller;


import james.LineBot.model.JavaLineBot;
import james.LineBot.model.Lesson;
import james.LineBot.repository.JavaLineBotRepository;
import james.LineBot.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/")
public class LessonController {

    @Autowired
    private LessonRepository lessonRepository;


    @PostMapping("/addLesson")
    public Lesson addLesson(@RequestBody Lesson lesson) {

        int index = lessonRepository.countByStatus(true);
        lesson.setIndex(index + 1);

        return lessonRepository.save(lesson);
    }

    @Autowired
    private JavaLineBotRepository javaLineBotRepository;
    @PostMapping("/addJavaLineBot")
    public JavaLineBot addJavaLineBot(@RequestBody JavaLineBot javaLineBot) {

        int index = javaLineBotRepository.countByStatus(true);
        javaLineBot.setIndex(index + 1);

        return javaLineBotRepository.save(javaLineBot);
    }

    @GetMapping("/count")
    public int countAll() {
        return lessonRepository.countByStatus(true);
    }


    @GetMapping("/getOne")
    public Lesson getOneLesson() {
        return lessonRepository.findByIndex(1);
    }


}
