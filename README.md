# Skill-Exchange – Android App Development using GenAI

## 📌 Project Overview

Skill-Exchange is a community-driven Android application designed for rural technicians and artisans to exchange services through a barter-based system instead of using money. The platform helps users discover skilled workers, post service needs, send swap offers, and build trust within local communities.

The application promotes self-employment, self-reliance, and collaboration by enabling users to trade services using a Skill Point system.

---

# 🎯 Problem Statement

Village technicians such as plumbers, electricians, carpenters, and mechanics often require help from other skilled workers but may not always have enough money to pay for services.

Although barter-based exchange is possible, there is no proper digital platform to:

* Discover available skills
* Request help
* Track service exchanges
* Maintain fairness and trust

Skill-Exchange solves this problem by creating a digital barter ecosystem.

---

# 💡 Project Vision

* Build a self-reliant community platform for technicians
* Promote skill-based collaboration and mutual support
* Digitize traditional barter systems using modern mobile technology
* Encourage trust and transparency through verified exchanges

---

# 🚀 Key Features

## 👤 Skill Profile

Users can:

* Create a profile
* Add primary and secondary skills
* Showcase expertise

## 📌 Need Posting

Users can:

* Post service requirements
* Mention required skill
* Add work description and estimated hours

## 🔍 Skill-Based Filtering

* Browse requests by required skills
* Easily find matching service opportunities

## 🔁 Swap Offer System

Users can:

* Send barter offers
* Exchange services using skill points

## ⭐ Trust Score System

* Trust score increases only after successful swap confirmation by both users
* Helps build reliability and credibility

## 💰 Skill Point System

* 1 hour of work = 1 skill point
* Maintains fairness in service exchange

---

# 🛠️ Technologies Used

* Kotlin
* Android Studio
* Jetpack Compose
* Firebase Firestore
* Firebase Authentication
* Material Design

---

# 📱 Application Modules

* User Authentication
* Skill Profile Management
* Need Posting System
* Swap Offer Management
* Skill Point Tracking
* Trust Score Management
* Transaction Tracking

---

# 🔄 User Flow

1. User Registration/Login
2. Create Skill Profile
3. Browse or Post Needs
4. Send/Receive Swap Offers
5. Accept Offer
6. Complete Task
7. Confirm Exchange
8. Trust Score & Skill Points Updated

---

# 📂 Firebase Collections Structure

## Users

* userId
* name
* primarySkill
* secondarySkills
* trustScore
* skillPoints

## Posts

* postId
* userId
* skillRequired
* description
* estimatedHours
* status

## Offers

* offerId
* senderId
* receiverId
* postId
* offeredSkill
* status

## Transactions

* transactionId
* participants
* points
* status
* confirmationFlags

---

# 🎨 UI/UX Design

* Clean and simple interface
* Community-friendly layout
* Responsive design using Jetpack Compose
* Low-bandwidth optimized screens
* Easy navigation and interactive components

---

# ✅ Current Progress

* Project planning completed
* UI screens implemented
* Firebase integration completed
* Skill profile and posting modules developed
* Navigation flow implemented
* Trust score logic added
* Testing and debugging in progress

---

# 🌍 Impact Goals

* Promote self-employment opportunities
* Reduce dependency on outside technicians
* Support local community collaboration
* Encourage skill recognition and trust-building

---

# 🔮 Future Enhancements

* Regional language support
* AI-based skill recommendations
* Voice input for low-literacy users
* Offline support for rural areas
* Real-time notifications

---

# 👨‍💻 Developed By

Android App Development using GenAI – Internship Project

Project Title: Skill-Exchange (Self-Employment)
