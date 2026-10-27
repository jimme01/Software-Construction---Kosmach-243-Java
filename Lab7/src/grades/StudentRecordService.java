package grades;

import java.math.BigDecimal;

public class StudentRecordService {
    private final AcademicRecordValidator validator;
    private final GpaCalculator gpaCalculator;
    private final ScholarshipPolicy scholarshipPolicy;
    private final TranscriptFormatter formatter;
    private final CsvRegistryWriter registryWriter;
    private final CuratorNotifier notifier;

    public StudentRecordService(AcademicRecordValidator validator, GpaCalculator gpaCalculator,
                                ScholarshipPolicy scholarshipPolicy, TranscriptFormatter formatter,
                                CsvRegistryWriter registryWriter, CuratorNotifier notifier) {
        this.validator = validator;
        this.gpaCalculator = gpaCalculator;
        this.scholarshipPolicy = scholarshipPolicy;
        this.formatter = formatter;
        this.registryWriter = registryWriter;
        this.notifier = notifier;
    }

    public ProcessingResult process(AcademicRecord record) {
        validator.validate(record);
        double gpa = gpaCalculator.calculate(record.grades());
        BigDecimal scholarship = scholarshipPolicy.calculate(record.grades(), gpa);
        String transcript = formatter.format(record, gpa, scholarship);
        registryWriter.write(record, gpa, scholarship);
        notifier.send(record.curatorEmail(), record.studentName(), transcript);
        return new ProcessingResult(gpa, scholarship, transcript);
    }
}
