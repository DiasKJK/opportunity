package com.diplom.diplom.configuration;

import com.diplom.diplom.features.authentication.model.AuthenticationUser;
import com.diplom.diplom.features.authentication.model.Role;
import com.diplom.diplom.features.authentication.repository.AuthenticationUserRepository;
import com.diplom.diplom.features.authentication.utils.Encoder;
import com.diplom.diplom.features.feed.model.Post;
import com.diplom.diplom.features.feed.repository.PostRepository;
import com.diplom.diplom.features.opportunity.Opportunity;
import com.diplom.diplom.features.opportunity.OpportunityCategory;
import com.diplom.diplom.features.opportunity.OpportunityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.diplom.diplom.features.application.model.Application;
import com.diplom.diplom.features.application.model.ApplicationStatus;
import com.diplom.diplom.features.application.repository.ApplicationRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

@Configuration
public class LoadDatabaseConfiguration {
    private final Encoder encoder;

    public LoadDatabaseConfiguration(Encoder encoder) {
        this.encoder = encoder;
    }

    @Bean
    public CommandLineRunner initDatabase(AuthenticationUserRepository authenticationUserRepository,
                                          PostRepository postRepository,
                                          OpportunityRepository opportunityRepository,
                                          ApplicationRepository applicationRepository) {
        return args -> {
            // Админ
            AuthenticationUser admin = new AuthenticationUser("dias@example.com", encoder.encode("dias"));
            admin.setRole(Role.ADMIN);
            admin.setFirstName("Dias");
            admin.setLastName("Adminov");
            admin.setGroup1("ADMIN-GROUP");
            admin.setStatus("Managing");
            admin.setAboutMe("System administrator");

            authenticationUserRepository.save(admin);

            List<AuthenticationUser> users = createUsers();
            authenticationUserRepository.saveAll(users);

            users.add(admin);

            createPosts(postRepository, users);
            createOpportunities(opportunityRepository);

            createApplications(applicationRepository, users, opportunityRepository);
        };
    }

    private List<AuthenticationUser> createUsers() {
        List<AuthenticationUser> users = new ArrayList<>();

        users.add(createUser("john.doe@example.com", "password123", "John", "Doe",
                "IT-2132", "Employed", "Computer Science", "Computer Science", "+77011234567", "Kazakhstan",
                "I am a passionate backend developer."));

        users.add(createUser("arslan.it@example.com", "password123", "Arslan", "Nurgali", "IT-2102", "Employed", "Computer Science", "Computer Science", "+77011234567", "Kazakhstan", "Backend developer."));
        users.add(createUser("aliya.it@example.com", "password123", "Aliya", "Sarsen", "IT-2103", "Looking", "Computer Science", "Computer Science", "+77007007070", "Kazakhstan", "Frontend enthusiast."));

        users.add(createUser("nursultan.se@example.com", "password123", "Nursultan", "Akhmetov", "SE-2102", "Employed", "Software Engineering", "Software Engineering", "+77015554433", "Kazakhstan", "Android developer."));
        users.add(createUser("dana.se@example.com", "password123", "Dana", "Kenzhebek", "SE-2103", "Looking", "Software Engineering", "Software Engineering", "+77019998877", "Kazakhstan", "Kotlin fan."));

        users.add(createUser("aidana.bda@example.com", "password123", "Aidana", "Zhanserik", "BDA-2102", "Looking", "Big Data Analysis", "Big Data Analysis", "+77012223344", "Kazakhstan", "Future data scientist."));
        users.add(createUser("temirlan.bda@example.com", "password123", "Temirlan", "Sultanov", "BDA-2103", "Employed", "Big Data Analysis", "Big Data Analysis", "+77016667788", "Kazakhstan", "ML enthusiast."));

        users.add(createUser("zhanel.mt@example.com", "password123", "Zhanel", "Nurmakhan", "MT-2102", "Looking", "Media Technologies", "Media Technologies", "+77017778899", "Kazakhstan", "Working in digital media."));
        users.add(createUser("meirzhan.mt@example.com", "password123", "Meirzhan", "Almasov", "MT-2103", "Employed", "Media Technologies", "Media Technologies", "+77013335555", "Kazakhstan", "Video editor."));

        users.add(createUser("zharaskan.mcs@example.com", "password123", "Zharaskan", "Baiterek", "MCS-2102", "Looking", "Mathematical and Computational science", "Mathematical and Computational science", "+77012221122", "Kazakhstan", "Math fan."));
        users.add(createUser("aigerim.mcs@example.com", "password123", "Aigerim", "Shyngys", "MCS-2103", "Employed", "Mathematical and Computational science", "Mathematical and Computational science", "+77014445566", "Kazakhstan", "Olympiad winner."));

        users.add(createUser("askar.bdh@example.com", "password123", "Askar", "Bekzhan", "BDH-2102", "Looking", "Big Data in Healthcare", "Big Data in Healthcare", "+77019997777", "Kazakhstan", "Healthcare data fan."));
        users.add(createUser("gulnara.bdh@example.com", "password123", "Gulnara", "Tileubek", "BDH-2103", "Employed", "Big Data in Healthcare", "Big Data in Healthcare", "+77015556666", "Kazakhstan", "Interested in biostatistics."));

        users.add(createUser("daulet.is@example.com", "password123", "Daulet", "Zhanadil", "IS-2102", "Looking", "Information Safety", "Information Safety", "+77012344321", "Kazakhstan", "Cyber hygiene promoter."));
        users.add(createUser("malika.is@example.com", "password123", "Malika", "Yesbol", "IS-2103", "Employed", "Information Safety", "Information Safety", "+77012224455", "Kazakhstan", "InfoSec learner."));

        users.add(createUser("yerkebulan.cs@example.com", "password123", "Yerkebulan", "Nurpeiis", "CS-2102", "Looking", "Cybersecurity", "Cybersecurity", "+77014449900", "Kazakhstan", "Wants to become ethical hacker."));
        users.add(createUser("ayana.cs@example.com", "password123", "Ayana", "Talgat", "CS-2103", "Employed", "Cybersecurity", "Cybersecurity", "+77018884455", "Kazakhstan", "Red team student."));

        users.add(createUser("marat.cct@example.com", "password123", "Marat", "Serikbay", "CCT-2102", "Employed", "Communications and Communication Technologies", "Communications and Communication Technologies", "+77017779999", "Kazakhstan", "Loves networks."));
        users.add(createUser("kamila.cct@example.com", "password123", "Kamila", "Nurtaeva", "CCT-2103", "Looking", "Communications and Communication Technologies", "Communications and Communication Technologies", "+77012221133", "Kazakhstan", "5G enthusiast."));

        users.add(createUser("sanzhar.st@example.com", "password123", "Sanzhar", "Yermaganbet", "ST-2102", "Looking", "Smart Technologies", "Smart Technologies", "+77018886655", "Kazakhstan", "Building smart homes."));
        users.add(createUser("sholpan.st@example.com", "password123", "Sholpan", "Myrzabai", "ST-2103", "Employed", "Smart Technologies", "Smart Technologies", "+77012228899", "Kazakhstan", "Smart devices developer."));

        users.add(createUser("ramazan.ea@example.com", "password123", "Ramazan", "Yerlanuly", "EA-2102", "Employed", "Engineering and Automation", "Engineering and Automation", "+77016665544", "Kazakhstan", "Control systems fan."));
        users.add(createUser("ainur.ea@example.com", "password123", "Ainur", "Bakbergen", "EA-2103", "Looking", "Engineering and Automation", "Engineering and Automation", "+77013336655", "Kazakhstan", "Loves robotics."));

        users.add(createUser("serik.iiot@example.com", "password123", "Serik", "Kalym", "IIoT-2102", "Looking", "Industrial Internet of Things", "Industrial Internet of Things", "+77018889900", "Kazakhstan", "Factory automation."));
        users.add(createUser("zhaniya.iiot@example.com", "password123", "Zhaniya", "Mukhamedi", "IIoT-2103", "Employed", "Industrial Internet of Things", "Industrial Internet of Things", "+77013334444", "Kazakhstan", "Industrial networks."));

        users.add(createUser("adilet.ee@example.com", "password123", "Adilet", "Abzal", "EE-2102", "Looking", "Electronic Engineering", "Electronic Engineering", "+77019991111", "Kazakhstan", "Loves circuits."));
        users.add(createUser("raushan.ee@example.com", "password123", "Raushan", "Samat", "EE-2103", "Employed", "Electronic Engineering", "Electronic Engineering", "+77018881111", "Kazakhstan", "Works with Arduino."));

        users.add(createUser("nurlan.itm@example.com", "password123", "Nurlan", "Omar", "ITM-2102", "Looking", "IT Management", "IT Management", "+77017776666", "Kazakhstan", "Future tech manager."));
        users.add(createUser("akmaral.itm@example.com", "password123", "Akmaral", "Zholdas", "ITM-2103", "Employed", "IT Management", "IT Management", "+77015559999", "Kazakhstan", "SCRUM master trainee."));

        users.add(createUser("erlan.ite@example.com", "password123", "Erlan", "Sayat", "ITE-2102", "Looking", "IT Entrepreneurship", "IT Entrepreneurship", "+77016668888", "Kazakhstan", "Tech startup founder."));
        users.add(createUser("gulzhan.ite@example.com", "password123", "Gulzhan", "Temir", "ITE-2103", "Employed", "IT Entrepreneurship", "IT Entrepreneurship", "+77017770000", "Kazakhstan", "Innovator."));

        users.add(createUser("dauren.aib@example.com", "password123", "Dauren", "Rakhim", "AIB-2102", "Looking", "AI Business", "AI Business", "+77014447777", "Kazakhstan", "AI in business."));

        users.add(createUser("zhansaya.aib@example.com", "password123", "Zhansaya", "Eset", "AIB-2103", "Employed", "AI Business", "AI Business", "+77012220000", "Kazakhstan", "AI strategist."));

        users.add(createUser("berik.dj@example.com", "password123", "Berik", "Nurlybek", "DJ-2102", "Looking", "Digital Journalism", "Digital Journalism", "+77015558888", "Kazakhstan", "Writes about science."));
        users.add(createUser("meruyert.dj@example.com", "password123", "Meruyert", "Asylbek", "DJ-2103", "Employed", "Digital Journalism", "Digital Journalism", "+77019993333", "Kazakhstan", "Investigative reporter."));

        return users;
    }

    private AuthenticationUser createUser(String email, String password, String firstName,
                                          String lastName, String groupName, String status,
                                          String group,
                                          String education,
                                          String contacts, String location, String aboutMe) {

        AuthenticationUser user = new AuthenticationUser(email, encoder.encode(password));
        user.setRole(Role.USER);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setGroup1(groupName);
        user.setStatus(status);

        user.setEducation(education);
        user.setContacts(contacts);
        user.setLocation(location);

        user.setAboutMe(aboutMe);

        user.setProfilePicture("https://ui-avatars.com/api/?name=" + firstName + "+" + lastName + "&background=random");

        return user;
    }

    private void createPosts(PostRepository postRepository, List<AuthenticationUser> users) {
        Random random = new Random();
        for (int j = 1; j <= 10; j++) {
            Post post = new Post("Innovation in tech is moving faster than ever!", users.get(random.nextInt(users.size())));
            post.setLikes(generateLikes(users, j, random));
            if (j == 1) {
                post.setPicture("https://plus.unsplash.com/premium_photo-1675848495392-6b9a3b962df0?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
            }
            postRepository.save(post);
        }
    }

    private HashSet<AuthenticationUser> generateLikes(List<AuthenticationUser> users, int postNumber, Random random) {
        HashSet<AuthenticationUser> likes = new HashSet<>();

        int likesCount = (postNumber == 1) ? 3 : switch (postNumber % 5) {
            case 2, 3 -> 2;
            default -> 1;
        };

        while (likes.size() < likesCount) {
            likes.add(users.get(random.nextInt(users.size())));
        }

        return likes;
    }

    private void createOpportunities(OpportunityRepository opportunityRepository) {
        List<Opportunity> opportunities = new ArrayList<>();

        // INTERNSHIP opportunities
        opportunities.add(createOpportunity(
                "https://images.unsplash.com/photo-1560472354-b33ff0c44a43?w=500",
                "Software Development Intern",
                "Kaspi Bank",
                "Almaty, Kazakhstan",
                OpportunityCategory.INTERNSHIP,
                Arrays.asList("2nd year", "3rd year"),
                "Join our development team to work on cutting-edge fintech solutions. This internship provides hands-on experience with modern technologies including Java, Spring Boot, and microservices architecture.",
                Arrays.asList("Basic knowledge of Java or similar programming language", "Understanding of OOP principles", "Good communication skills in English and Kazakh/Russian"),
                Arrays.asList("Develop and maintain web applications", "Participate in code reviews", "Write unit tests", "Collaborate with senior developers"),
                Arrays.asList("Competitive salary", "Mentorship program", "Free meals", "Flexible working hours"),
                LocalDate.now().plusDays(45),
                "internship@kaspi.kz",
                "+7 727 244 4444"
        ));

        opportunities.add(createOpportunity(
                "https://images.unsplash.com/photo-1551434678-e076c223a692?w=500",
                "Data Science Intern",
                "Beeline Kazakhstan",
                "Almaty, Kazakhstan",
                OpportunityCategory.INTERNSHIP,
                Arrays.asList("3rd year", "4th year"),
                "Work with our analytics team to extract insights from telecommunications data. Learn machine learning techniques applied to real business problems.",
                Arrays.asList("Python programming skills", "Basic statistics knowledge", "Familiarity with pandas, numpy", "Interest in data analysis"),
                Arrays.asList("Analyze customer behavior data", "Create data visualizations", "Assist in building ML models", "Prepare analytical reports"),
                Arrays.asList("Professional development", "Access to online courses", "Team building events", "Recommendation letter"),
                LocalDate.now().plusDays(30),
                "careers@beeline.kz",
                "+7 727 258 0000"
        ));

        opportunities.add(createOpportunity(
                "https://images.unsplash.com/photo-1553877522-43269d4ea984?w=500",
                "Mobile Development Intern",
                "Chocofamily",
                "Almaty, Kazakhstan",
                OpportunityCategory.INTERNSHIP,
                Arrays.asList("2nd year", "3rd year", "4th year"),
                "Join our mobile team to develop iOS and Android applications for food delivery and e-commerce platforms serving millions of users across Kazakhstan.",
                Arrays.asList("Knowledge of Swift/Kotlin or React Native", "Understanding of mobile app architecture", "Experience with Git"),
                Arrays.asList("Develop mobile app features", "Fix bugs and optimize performance", "Participate in app testing", "Learn from senior mobile developers"),
                Arrays.asList("Mentorship from industry experts", "Real project experience", "Networking opportunities", "Possible full-time offer"),
                LocalDate.now().plusDays(60),
                "mobile-internship@choco.kz",
                "+7 727 266 7777"
        ));

        // VOLUNTEERING opportunities
        opportunities.add(createOpportunity(
                "https://images.unsplash.com/photo-1559027615-cd4628902d4a?w=500",
                "Tech4Good Volunteer Developer",
                "Digital Kazakhstan Foundation",
                "Nur-Sultan, Kazakhstan",
                OpportunityCategory.VOLUNTEERING,
                Arrays.asList("1st year", "2nd year", "3rd year", "4th year"),
                "Help develop digital solutions for social impact projects. Work on applications that improve access to education and healthcare in rural areas of Kazakhstan.",
                Arrays.asList("Any programming language knowledge", "Passion for social impact", "Basic web development skills"),
                Arrays.asList("Develop web applications for NGOs", "Create educational content", "Test and deploy applications", "Collaborate with international volunteers"),
                Arrays.asList("Social impact experience", "International networking", "Certificate of volunteering", "Portfolio projects"),
                LocalDate.now().plusDays(90),
                "volunteers@digitalkazakhstan.org",
                "+7 717 242 4242"
        ));

        opportunities.add(createOpportunity(
                "https://images.unsplash.com/photo-1488521787991-ed7bbaae773c?w=500",
                "Digital Literacy Trainer",
                "UNICEF Kazakhstan",
                "Various locations",
                OpportunityCategory.VOLUNTEERING,
                Arrays.asList("2nd year", "3rd year", "4th year"),
                "Train teachers and students in rural schools on basic computer skills and digital tools. Help bridge the digital divide in Kazakhstan's education system.",
                Arrays.asList("Good communication skills", "Patience and empathy", "Basic computer literacy", "Willingness to travel"),
                Arrays.asList("Conduct training sessions", "Create educational materials", "Assess training effectiveness", "Report progress to coordinators"),
                Arrays.asList("Training certification", "Travel expenses covered", "Meaningful social impact", "International organization experience"),
                LocalDate.now().plusDays(75),
                "education@unicef.org",
                "+7 727 258 26 43"
        ));

        opportunities.add(createOpportunity(
                "https://images.unsplash.com/photo-1516321318423-f06f85e504b3?w=500",
                "Open Source Contributor",
                "Kazakhstan Open Source Community",
                "Remote",
                OpportunityCategory.VOLUNTEERING,
                Arrays.asList("1st year", "2nd year", "3rd year", "4th year"),
                "Contribute to open source projects used by Kazakhstani government and educational institutions. Gain experience with collaborative development.",
                Arrays.asList("Git/GitHub knowledge", "Any programming language", "Interest in open source", "Self-motivated"),
                Arrays.asList("Fix bugs in existing projects", "Add new features", "Write documentation", "Review code from other contributors"),
                Arrays.asList("Open source portfolio", "GitHub contributions", "Technical mentorship", "Community recognition"),
                LocalDate.now().plusDays(120),
                "community@opensource.kz",
                "No phone contact - email only"
        ));

        // JOB opportunities
        opportunities.add(createOpportunity(
                "https://images.unsplash.com/photo-1486312338219-ce68d2c6f44d?w=500",
                "Junior Full Stack Developer",
                "Kcell",
                "Almaty, Kazakhstan",
                OpportunityCategory.JOB,
                Arrays.asList("4th year", "Graduate"),
                "Full-time position for a junior developer to work on customer-facing web applications and internal tools for Kazakhstan's leading mobile operator.",
                Arrays.asList("1+ years experience with JavaScript/TypeScript", "Knowledge of React or Vue.js", "Basic backend experience (Node.js/Python/Java)", "English proficiency"),
                Arrays.asList("Develop and maintain web applications", "Work with cross-functional teams", "Participate in agile development process", "Optimize application performance"),
                Arrays.asList("Competitive salary 400,000-600,000 KZT", "Health insurance", "Professional development budget", "Flexible working arrangements"),
                LocalDate.now().plusDays(40),
                "careers@kcell.kz",
                "+7 727 258 5555"
        ));

        opportunities.add(createOpportunity(
                "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=500",
                "Junior DevOps Engineer",
                "Halyk Bank",
                "Almaty, Kazakhstan",
                OpportunityCategory.JOB,
                Arrays.asList("4th year", "Graduate"),
                "Join our infrastructure team to manage and automate deployment processes for one of Kazakhstan's largest banks. Work with modern DevOps tools and cloud technologies.",
                Arrays.asList("Knowledge of Linux systems", "Basic Docker/Kubernetes experience", "Understanding of CI/CD concepts", "Scripting skills (Bash/Python)"),
                Arrays.asList("Maintain CI/CD pipelines", "Monitor system performance", "Automate deployment processes", "Collaborate with development teams"),
                Arrays.asList("Salary 500,000-700,000 KZT", "Bank benefits package", "Training and certification support", "Career growth opportunities"),
                LocalDate.now().plusDays(35),
                "devops-jobs@halykbank.kz",
                "+7 727 259 0000"
        ));

        opportunities.add(createOpportunity(
                "https://images.unsplash.com/photo-1560472354-b33ff0c44a43?w=500",
                "Junior QA Engineer",
                "Technodom",
                "Almaty, Kazakhstan",
                OpportunityCategory.JOB,
                Arrays.asList("3rd year", "4th year", "Graduate"),
                "Quality assurance position for e-commerce platform testing. Learn automated testing tools and ensure high-quality user experience for online shoppers.",
                Arrays.asList("Understanding of testing methodologies", "Basic automation skills (Selenium preferred)", "Attention to detail", "English proficiency"),
                Arrays.asList("Perform manual and automated testing", "Write test cases and bug reports", "Collaborate with developers", "Participate in release planning"),
                Arrays.asList("Salary 350,000-500,000 KZT", "Professional training", "Health insurance", "Employee discounts"),
                LocalDate.now().plusDays(50),
                "qa-jobs@technodom.kz",
                "+7 727 344 4444"
        ));

        // RESEARCH opportunities
        opportunities.add(createOpportunity(
                "https://images.unsplash.com/photo-1451187580459-43490279c0fa?w=500",
                "AI Research Assistant",
                "Nazarbayev University",
                "Nur-Sultan, Kazakhstan",
                OpportunityCategory.RESEARCH,
                Arrays.asList("3rd year", "4th year", "Graduate"),
                "Research position in computer vision and natural language processing lab. Work on cutting-edge AI projects with applications in Kazakhstani industries.",
                Arrays.asList("Strong mathematics background", "Python programming skills", "Familiarity with TensorFlow/PyTorch", "Research experience preferred"),
                Arrays.asList("Conduct literature reviews", "Implement research algorithms", "Collect and analyze data", "Co-author research papers"),
                Arrays.asList("Research stipend", "Conference attendance opportunities", "Publication credits", "PhD pathway guidance"),
                LocalDate.now().plusDays(25),
                "research@nu.edu.kz",
                "+7 717 706 4444"
        ));

        opportunities.add(createOpportunity(
                "https://images.unsplash.com/photo-1532094349884-543bc11b234d?w=500",
                "Cybersecurity Research Intern",
                "Kazakhstan Institute of Information Security",
                "Almaty, Kazakhstan",
                OpportunityCategory.RESEARCH,
                Arrays.asList("3rd year", "4th year"),
                "Research internship focusing on cybersecurity threats specific to Central Asian region. Analyze attack patterns and develop defense mechanisms.",
                Arrays.asList("Cybersecurity fundamentals", "Network security knowledge", "Programming skills (Python/C++)", "Analytical thinking"),
                Arrays.asList("Analyze malware samples", "Research threat intelligence", "Develop security tools", "Write technical reports"),
                Arrays.asList("Research experience", "Security clearance path", "Industry connections", "Conference presentation opportunities"),
                LocalDate.now().plusDays(55),
                "research@kiis.kz",
                "+7 727 266 8888"
        ));

        opportunities.add(createOpportunity(
                "https://images.unsplash.com/photo-1518709268805-4e9042af2176?w=500",
                "Blockchain Research Project",
                "KIMEP University",
                "Almaty, Kazakhstan",
                OpportunityCategory.RESEARCH,
                Arrays.asList("4th year", "Graduate"),
                "Research project investigating blockchain applications for supply chain management in Kazakhstan's agricultural sector. Collaborate with international partners.",
                Arrays.asList("Blockchain fundamentals", "Smart contract development", "Data analysis skills", "Interest in agriculture/economics"),
                Arrays.asList("Study existing blockchain solutions", "Develop proof-of-concept applications", "Analyze economic impacts", "Present findings at conferences"),
                Arrays.asList("Research grant funding", "International collaboration", "Patent opportunities", "Academic career preparation"),
                LocalDate.now().plusDays(70),
                "blockchain-research@kimep.kz",
                "+7 727 270 4444"
        ));

        // PART_TIME opportunities
        opportunities.add(createOpportunity(
                "https://images.unsplash.com/photo-1522202176988-66273c2fd55f?w=500",
                "Part-time Web Developer",
                "Creative Digital Agency",
                "Almaty, Kazakhstan",
                OpportunityCategory.PART_TIME,
                Arrays.asList("2nd year", "3rd year", "4th year"),
                "Flexible part-time position creating websites for local businesses. Perfect for students who want to gain real-world experience while studying.",
                Arrays.asList("HTML, CSS, JavaScript knowledge", "Familiarity with WordPress or similar CMS", "Design sense helpful", "Time management skills"),
                Arrays.asList("Build websites for clients", "Maintain existing sites", "Collaborate with design team", "Communicate with clients"),
                Arrays.asList("Flexible schedule", "Hourly rate 3,000-5,000 KZT", "Portfolio building", "Client interaction experience"),
                LocalDate.now().plusDays(80),
                "jobs@creativedigital.kz",
                "+7 727 333 2222"
        ));

        opportunities.add(createOpportunity(
                "https://images.unsplash.com/photo-1556742049-0cfed4f6a45d?w=500",
                "Part-time Mobile App Tester",
                "Startup Incubator Astana",
                "Nur-Sultan, Kazakhstan",
                OpportunityCategory.PART_TIME,
                Arrays.asList("1st year", "2nd year", "3rd year"),
                "Test mobile applications developed by startups in our incubator. Flexible hours that work around your class schedule.",
                Arrays.asList("Smartphone ownership", "Attention to detail", "Good communication skills", "Interest in new technologies"),
                Arrays.asList("Test mobile applications", "Report bugs and usability issues", "Provide user experience feedback", "Attend weekly team meetings"),
                Arrays.asList("Flexible 10-20 hours/week", "Hourly rate 2,500 KZT", "Early access to new apps", "Startup ecosystem exposure"),
                LocalDate.now().plusDays(65),
                "testing@incubator-astana.kz",
                "+7 717 200 3333"
        ));

        opportunities.add(createOpportunity(
                "https://images.unsplash.com/photo-1542744173-8e7e53415bb0?w=500",
                "Part-time IT Support",
                "AITU Campus",
                "Almaty, Kazakhstan",
                OpportunityCategory.PART_TIME,
                Arrays.asList("1st year", "2nd year", "3rd year", "4th year"),
                "Provide technical support for students and faculty on campus. Great opportunity to help your fellow students while earning money.",
                Arrays.asList("Basic computer troubleshooting skills", "Good interpersonal skills", "Patience and helpfulness", "Knowledge of Windows/Mac/Linux"),
                Arrays.asList("Help students with technical issues", "Maintain computer labs", "Install and update software", "Create user guides and tutorials"),
                Arrays.asList("On-campus work", "Flexible student hours", "Rate 2,000-3,000 KZT/hour", "Skill development"),
                LocalDate.now().plusDays(100),
                "support@aitu.edu.kz",
                "+7 727 320 7777"
        ));

        opportunityRepository.saveAll(opportunities);
    }

    private Opportunity createOpportunity(String image, String title, String company, String location,
                                          OpportunityCategory category, List<String> yearOfStudy,
                                          String description, List<String> requirements,
                                          List<String> responsibilities, List<String> benefits,
                                          LocalDate deadline, String email, String phone) {
        Opportunity opportunity = new Opportunity();
        opportunity.setImage(image);
        opportunity.setTitle(title);
        opportunity.setCompany(company);
        opportunity.setLocation(location);
        opportunity.setCategory(category);
        opportunity.setYearOfStudy(yearOfStudy);
        opportunity.setDescription(description);
        opportunity.setRequirements(requirements);
        opportunity.setResponsibilities(responsibilities);
        opportunity.setBenefits(benefits);
        opportunity.setDeadline(deadline);
        // Note: email and phone fields are not in the Opportunity entity you provided
        // You may need to add these fields to the entity if needed
        return opportunity;
    }

    private void createApplications(ApplicationRepository applicationRepository,
                                    List<AuthenticationUser> users,
                                    OpportunityRepository opportunityRepository) {
        List<Opportunity> opportunities = opportunityRepository.findAll();

        if (opportunities.isEmpty() || users.isEmpty()) {
            return;
        }

        Random random = new Random();

        // Создать 3 тестовых заявки
        // 1. PENDING заявка
        Application pendingApp = new Application(
                users.get(random.nextInt(users.size())),
                opportunities.get(0),
                "I am very interested in this internship opportunity. I have been studying software development for 2 years and believe this position would be perfect for gaining practical experience."
        );
        pendingApp.setStatus(ApplicationStatus.PENDING);

        // 2. ACCEPTED заявка
        Application acceptedApp = new Application(
                users.get(random.nextInt(users.size())),
                opportunities.get(1),
                "I am excited to apply for this data science internship. My background in statistics and Python programming makes me a strong candidate for this role."
        );
        acceptedApp.setStatus(ApplicationStatus.ACCEPTED);
        acceptedApp.setAdminNotes("Great candidate with strong technical skills. Approved for interview.");

        // 3. REJECTED заявка
        Application rejectedApp = new Application(
                users.get(random.nextInt(users.size())),
                opportunities.get(2),
                "I would like to apply for this mobile development position. I am eager to learn and contribute to your team."
        );
        rejectedApp.setStatus(ApplicationStatus.REJECTED);
        rejectedApp.setAdminNotes("Unfortunately, the candidate doesn't meet the minimum requirements for this position.");

        applicationRepository.saveAll(Arrays.asList(pendingApp, acceptedApp, rejectedApp));
    }
}