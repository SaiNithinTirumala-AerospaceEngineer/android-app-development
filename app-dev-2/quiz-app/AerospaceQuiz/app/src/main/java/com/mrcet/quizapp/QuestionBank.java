package com.mrcet.quizapp;

/**
 * QuestionBank — 15 Aerospace and General Science Questions
 *
 * Questions drawn from aeronautical engineering fundamentals,
 * physics, and general science — relevant to B.Tech Aeronautical
 * Engineering curriculum at MRCET.
 *
 * Course:      Application Development 2
 * Institution: MRCET, Department of Aeronautical Engineering
 * Guide:       Mrs. L. Sushma, Associate Professor
 * Year:        2022–2023
 */
public class QuestionBank {

    public static Question[] getQuestions() {
        return new Question[]{

                // Aeronautical Engineering
                new Question(
                        "What does the acronym 'VTOL' stand for?",
                        new String[]{
                                "Vertical Take-Off and Landing",
                                "Variable Thrust Output Level",
                                "Vectored Turbine Output Lift",
                                "Vertical Torque Oscillation Limit"
                        }, 0
                ),
                new Question(
                        "Which control surface on an aircraft controls roll?",
                        new String[]{"Rudder", "Elevator", "Aileron", "Flap"},
                        2
                ),
                new Question(
                        "What is the Bernoulli principle primarily used to explain?",
                        new String[]{
                                "Engine thrust",
                                "Aerodynamic lift",
                                "Radar wave propagation",
                                "Fuel combustion"
                        }, 1
                ),
                new Question(
                        "The speed of sound at sea level (ISA) is approximately:",
                        new String[]{"240 m/s", "331 m/s", "400 m/s", "500 m/s"},
                        1
                ),
                new Question(
                        "Which aerodynamic force acts opposite to the direction of motion?",
                        new String[]{"Lift", "Thrust", "Weight", "Drag"},
                        3
                ),
                new Question(
                        "A Mach number of 1.0 means the aircraft is flying at:",
                        new String[]{
                                "Half the speed of sound",
                                "Exactly the speed of sound",
                                "Twice the speed of sound",
                                "The speed of light"
                        }, 1
                ),
                new Question(
                        "What does 'AGL' stand for in aviation?",
                        new String[]{
                                "Above Ground Level",
                                "Adjusted Glide Limit",
                                "Airborne GPS Locator",
                                "Aircraft Ground Load"
                        }, 0
                ),
                new Question(
                        "The angle between the chord line and the relative wind is called:",
                        new String[]{
                                "Dihedral angle",
                                "Sweep angle",
                                "Angle of attack",
                                "Aspect ratio"
                        }, 2
                ),

                // Physics and Engineering
                new Question(
                        "Newton's third law states that for every action there is:",
                        new String[]{
                                "An equal reaction in the same direction",
                                "An equal and opposite reaction",
                                "A greater reaction in the opposite direction",
                                "No reaction in a vacuum"
                        }, 1
                ),
                new Question(
                        "What is the SI unit of force?",
                        new String[]{"Watt", "Pascal", "Newton", "Joule"},
                        2
                ),
                new Question(
                        "Which gas makes up approximately 78% of Earth's atmosphere?",
                        new String[]{"Oxygen", "Carbon dioxide", "Nitrogen", "Argon"},
                        2
                ),
                new Question(
                        "The International Standard Atmosphere (ISA) defines sea-level" +
                                " temperature as:",
                        new String[]{"0°C", "15°C", "25°C", "37°C"},
                        1
                ),

                // Android / Technology
                new Question(
                        "Android operating system is based on which kernel?",
                        new String[]{"Windows NT", "Darwin", "Linux", "BSD"},
                        2
                ),
                new Question(
                        "What does 'SDK' stand for in Android development?",
                        new String[]{
                                "System Design Kit",
                                "Software Development Kit",
                                "Source Data Key",
                                "Structured Development Kit"
                        }, 1
                ),
                new Question(
                        "Which programming language was originally used for Android development?",
                        new String[]{"Python", "C++", "Kotlin", "Java"},
                        3
                ),
        };
    }
}
