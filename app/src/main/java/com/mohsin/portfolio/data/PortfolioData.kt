package com.mohsin.portfolio.data

import com.mohsin.portfolio.models.ContactInfo
import com.mohsin.portfolio.models.CredentialType
import com.mohsin.portfolio.models.Certification
import com.mohsin.portfolio.models.Education
import com.mohsin.portfolio.models.Experience
import com.mohsin.portfolio.models.Metric
import com.mohsin.portfolio.models.Profile
import com.mohsin.portfolio.models.Project
import com.mohsin.portfolio.models.SkillGroup

object PortfolioData {

    val profile = Profile(
        fullName = "Mohd Mohsin",
        headline = "Building meaningful digital experiences.",
        location = "Lucknow, India",
        intro = "I’m Mohd Mohsin, a Computer Science and Engineering undergraduate at Integral University, Lucknow, with freelance experience developing responsive web applications. My interests span modern web technologies, AI-assisted development, cloud computing, and thoughtfully designed digital products.",
        bioParagraphs = listOf(
            "Mohd Mohsin is a Front-End Web Developer with freelance experience designing, developing, testing, and deploying responsive web applications. His work involves modern frontend technologies, reusable UI components, performance optimization, REST API integration, and cross-browser compatibility.",
            "He is pursuing a Bachelor of Technology in Computer Science and Engineering at Integral University, Lucknow. His technical interests extend to cloud computing, large language models, prompt engineering, and AI-assisted development.",
            "Alongside his development work, he serves as a Campus Ambassador for the Entrepreneurship Cell at IIT Bombay, contributing to entrepreneurship awareness, outreach activities, and student engagement."
        ),
        descriptors = listOf(
            "Front-End Web Developer",
            "Computer Science & Engineering Undergraduate",
            "AI-Assisted Development Enthusiast"
        ),
        philosophy = listOf(
            "Clarity before complexity",
            "Building usable technology",
            "Continuous self-learning",
            "Thoughtful interface design",
            "Practical problem-solving",
            "Delivering reliable results"
        )
    )

    val experiences = listOf(
        Experience(
            role = "Freelance Web Developer",
            organization = "Self-Employed",
            organizationDetail = null,
            location = "Remote",
            duration = "Aug 2024 — Present",
            bullets = listOf(
                "Designed, developed, tested, and deployed more than five responsive client websites and web applications.",
                "Used HTML5, CSS3, JavaScript, React.js, and Next.js.",
                "Achieved a 100% on-time delivery record across client engagements.",
                "Reduced average page-load time by 28% through CSS minification, image lazy loading, DOM depth reduction, and asset optimization.",
                "Improved Google Lighthouse mobile usability scores by 30%.",
                "Created over 18 reusable interface components.",
                "Resolved more than 40 cross-browser compatibility issues.",
                "Integrated REST APIs to provide dynamic content.",
                "Applied AI-assisted development and prompt engineering to reduce average development time per feature by 20%.",
                "Managed more than 10 GitHub repositories.",
                "Worked across requirement analysis, wireframing, development, testing, deployment, and maintenance.",
                "Communicated project progress and technical decisions directly with clients."
            )
        ),
        Experience(
            role = "Campus Ambassador",
            organization = "Entrepreneurship Cell, IIT Bombay",
            organizationDetail = "Integral University, Lucknow",
            location = "Lucknow, India",
            duration = "Jun 2026 — Present",
            bullets = listOf(
                "Represented E-Cell IIT Bombay and promoted entrepreneurship and innovation among a campus community of 500+ peers.",
                "Coordinated more than three outreach campaigns, webinars, and campus workshops.",
                "Generated more than 100 student registrations for national programs and competitions.",
                "Increased student engagement by 40% through targeted content and campaigns.",
                "Represented the initiative across more than five campus events and virtual sessions.",
                "Coordinated with the central team to support national entrepreneurship initiatives."
            )
        )
    )

    val freelanceHighlights = listOf(
        Metric("5+", "Client projects delivered"),
        Metric("100%", "On-time delivery"),
        Metric("28%", "Faster page loads"),
        Metric("18+", "Reusable components")
    )

    val projects = listOf(
        Project(
            id = "web-suite",
            index = "01",
            title = "Responsive Web Application Suite",
            category = "Web Applications",
            year = "2024 — 2025",
            description = "A collection of responsive web applications developed with a focus on usability, interface consistency, performance, and cross-browser compatibility.",
            technologies = listOf("HTML5", "CSS3", "JavaScript", "React.js", "Tailwind CSS", "Git", "GitHub", "Netlify"),
            contributions = listOf(
                "Developed more than three responsive web applications.",
                "Achieved Google Lighthouse performance scores of 85 or higher.",
                "Built more than 18 interactive UI features.",
                "Reduced average page-load time by 28%.",
                "Resolved more than 40 browser rendering inconsistencies.",
                "Used AI-assisted content workflows to reduce manual work by approximately 25%.",
                "Deployed applications through Netlify with GitHub-connected continuous deployment."
            ),
            metrics = listOf(
                Metric("3+", "Applications"),
                Metric("85+", "Lighthouse score"),
                Metric("28%", "Faster loads")
            ),
            liveUrl = null,
            githubUrl = null
        ),
        Project(
            id = "portfolio-site",
            index = "02",
            title = "Personal Portfolio Website",
            category = "Personal Brand",
            year = "2024",
            description = "A responsive personal portfolio created to present projects, technical expertise, certifications, and professional experience.",
            technologies = listOf("HTML5", "CSS3", "JavaScript", "Tailwind CSS", "Netlify"),
            contributions = listOf(
                "Built a responsive, mobile-first interface.",
                "Presented more than five projects.",
                "Implemented semantic HTML.",
                "Achieved Google Lighthouse scores of at least 90 across Performance, Accessibility, Best Practices, and SEO.",
                "Implemented adaptive layouts for different screen sizes.",
                "Deployed with GitHub-based continuous deployment on Netlify."
            ),
            metrics = listOf(
                Metric("90+", "Lighthouse scores"),
                Metric("5+", "Projects shown"),
                Metric("4/4", "Lighthouse categories")
            ),
            liveUrl = "https://mohdmohsinportfolio.netlify.app",
            githubUrl = null
        )
    )

    val skillGroups = listOf(
        SkillGroup(
            "Frontend Languages",
            listOf("HTML5", "CSS3", "JavaScript", "TypeScript Basics")
        ),
        SkillGroup(
            "Frameworks & Libraries",
            listOf("React.js", "Next.js", "Tailwind CSS", "Bootstrap")
        ),
        SkillGroup(
            "Web Development",
            listOf(
                "Responsive Web Design", "Mobile-First Design", "DOM Manipulation",
                "Event Handling", "Dynamic Content Rendering", "Form Validation",
                "REST API Integration", "Cross-Browser Compatibility", "SEO Optimization",
                "Web Accessibility", "Semantic HTML"
            )
        ),
        SkillGroup(
            "Performance Optimization",
            listOf(
                "Google Lighthouse Audits", "Lazy Loading", "CSS Minification",
                "Page Speed Optimization", "Code Splitting", "Asset Optimization"
            )
        ),
        SkillGroup(
            "Version Control",
            listOf("Git", "GitHub", "Feature Branching", "Merging", "Pull Requests", "Code Reviews", "Repository Documentation")
        ),
        SkillGroup(
            "Cloud Computing",
            listOf("AWS Solutions Architecture", "Amazon Web Services", "Oracle Cloud Infrastructure", "Cloud Fundamentals", "Serverless Basics")
        ),
        SkillGroup(
            "Artificial Intelligence",
            listOf("Large Language Models", "Prompt Engineering", "Generative AI", "AI-Assisted Development", "NLP Basics")
        ),
        SkillGroup(
            "Tools & Platforms",
            listOf("Visual Studio Code", "Netlify", "Vercel", "Chrome DevTools", "Figma Basics", "Postman")
        ),
        SkillGroup(
            "Professional Skills",
            listOf(
                "Problem Solving", "Client Communication", "Agile Methodology",
                "Time Management", "Attention to Detail", "Team Collaboration",
                "Self-Learning", "Technical Documentation"
            )
        )
    )

    val education = Education(
        degree = "Bachelor of Technology — Computer Science and Engineering",
        institution = "Integral University",
        location = "Lucknow, Uttar Pradesh, India",
        duration = "2024 — Present",
        coursework = listOf(
            "Data Structures and Algorithms",
            "Web Technologies",
            "Object-Oriented Programming",
            "Database Management Systems",
            "Computer Networks",
            "Operating Systems",
            "Software Engineering",
            "Artificial Intelligence Fundamentals"
        )
    )

    val certifications = listOf(
        Certification(
            "Oracle Cloud Infrastructure 2025 Artificial Intelligence Foundations Associate",
            "Oracle University", "December 2025", CredentialType.CERTIFICATION, null
        ),
        Certification(
            "How Large Language Models Work",
            "IBM", "August 2025", CredentialType.CERTIFICATION, null
        ),
        Certification(
            "Introduction to Generative Artificial Intelligence",
            "Google Cloud", "2025", CredentialType.CERTIFICATION, null
        ),
        Certification(
            "AWS Solutions Architecture Virtual Job Simulation",
            "Amazon Web Services via Forage", "December 2025", CredentialType.SIMULATION, null
        ),
        Certification(
            "Software Engineering Virtual Job Simulation",
            "JPMorgan Chase & Co. via Forage", "December 2025", CredentialType.SIMULATION, null
        ),
        Certification(
            "Technology Consulting Virtual Job Simulation",
            "Deloitte Australia via Forage", "December 2025", CredentialType.SIMULATION, null
        ),
        Certification(
            "Cybersecurity Virtual Job Simulation",
            "Deloitte Australia via Forage", "December 2025", CredentialType.SIMULATION, null
        ),
        Certification(
            "Amazon Prime Video Technology Career Experience",
            "Springpod", "December 2025", CredentialType.EXPERIENCE, null
        ),
        Certification(
            "BSc Honours Computer Science University Taster Programme",
            "Springpod", "December 2025", CredentialType.PROGRAMME, null
        )
    )

    val impactMetrics = listOf(
        Metric("5+", "Freelance projects delivered"),
        Metric("100%", "On-time delivery record"),
        Metric("10+", "Virtual job simulations completed"),
        Metric("25+", "Engineering challenges solved"),
        Metric("40%", "Campus engagement increase"),
        Metric("28%", "Average page-load improvement")
    )

    val beyondTheCode = listOf(
        "Entrepreneurship",
        "Creative problem-solving",
        "Design",
        "Continuous learning"
    )

    val contact = ContactInfo(
        email = "rizvimohdmohsin@gmail.com",
        phone = "+91-9336701587",
        linkedIn = "https://linkedin.com/in/mohd-mohsin-rizvi",
        gitHub = "https://github.com/mohdmohsinrizvi",
        portfolio = "https://mohdmohsinportfolio.netlify.app",
        location = "Lucknow, Uttar Pradesh, India",
        availability = "Immediately available for full-time, part-time, internship, contract, and remote opportunities.",
        workAuthorization = "Indian citizen, authorized to work in India.",
        languages = listOf(
            "English — Professional Working Proficiency",
            "Hindi — Native Speaker"
        ),
        interests = listOf(
            "Open Source Development",
            "AI-Powered Web Applications",
            "Cloud-Native Development",
            "UI/UX Design",
            "Entrepreneurship"
        )
    )

    val whatICreate = listOf(
        "Web development",
        "Interface engineering",
        "AI-assisted workflows",
        "Cloud-related interests"
    )
}
