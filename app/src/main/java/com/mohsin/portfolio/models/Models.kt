package com.mohsin.portfolio.models

data class Profile(
    val fullName: String,
    val headline: String,
    val location: String,
    val intro: String,
    val bioParagraphs: List<String>,
    val descriptors: List<String>,
    val philosophy: List<String>
)

data class Experience(
    val role: String,
    val organization: String,
    val organizationDetail: String?,
    val location: String,
    val duration: String,
    val bullets: List<String>
)

data class Project(
    val id: String,
    val index: String,
    val title: String,
    val category: String,
    val year: String,
    val description: String,
    val technologies: List<String>,
    val contributions: List<String>,
    val metrics: List<Metric>,
    val liveUrl: String?,
    val githubUrl: String?
)

data class Metric(
    val value: String,
    val label: String
)

data class SkillGroup(
    val title: String,
    val skills: List<String>
)

data class Education(
    val degree: String,
    val institution: String,
    val location: String,
    val duration: String,
    val coursework: List<String>
)

enum class CredentialType(val badge: String) {
    CERTIFICATION("CERTIFICATION"),
    SIMULATION("VIRTUAL SIMULATION"),
    PROGRAMME("TASTER PROGRAMME"),
    EXPERIENCE("CAREER EXPERIENCE")
}

data class Certification(
    val title: String,
    val issuer: String,
    val date: String?,
    val type: CredentialType,
    val verificationUrl: String?
)

data class ContactInfo(
    val email: String,
    val phone: String,
    val linkedIn: String,
    val gitHub: String,
    val portfolio: String,
    val location: String,
    val availability: String,
    val workAuthorization: String,
    val languages: List<String>,
    val interests: List<String>
)
