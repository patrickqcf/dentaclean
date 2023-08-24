
plugins {
	java
	id("io.qameta.allure") version "2.10.0"
}

allure {
	version.set("2.10.0")
	autoconfigure = true
	report.reportDir.set(file("report"))
	aspectjweaver = true
}

group = "org.dentaclean"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11
java.targetCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
	implementation("com.github.javafaker:javafaker:1.0.2")
	testImplementation("junit:junit:4.13.1")
	testImplementation("io.rest-assured:rest-assured:5.3.1")
}

tasks.withType<JavaCompile> {
	options.encoding = "UTF-8"
}

tasks.test {
	useJUnit()
	finalizedBy("allureReport")
}

tasks.withType<Test> {
	delete(file("report/allureReport"))
	testLogging {
		events("passed", "skipped", "failed")
		showExceptions = true
		showCauses = true
		showStackTraces = true
		showStandardStreams = true
	}
 }

