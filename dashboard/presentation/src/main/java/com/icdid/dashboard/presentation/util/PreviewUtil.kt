package com.icdid.dashboard.presentation.util

import com.icdid.dashboard.domain.model.NoteDomain
import java.util.UUID

object PreviewUtil {
    val noteSamples = listOf(
        // SHORT CONTENT
        NoteDomain(
            id = UUID.randomUUID().toString(),
            title = "Quick Reminder",
            content = "Buy milk and eggs from the store.",
            createdAt = "2025-06-20T08:30:00Z",
            lastEditedAt = "2025-06-20T08:30:00Z"
        ),
        NoteDomain(
            id = UUID.randomUUID().toString(),
            title = "Meeting Note",
            content = "Call John at 3 PM today.",
            createdAt = "2025-06-19T14:15:00Z",
            lastEditedAt = "2025-06-19T14:15:00Z"
        ),
        NoteDomain(
            id = UUID.randomUUID().toString(),
            title = "Password",
            content = "New WiFi password: SecureNet2024",
            createdAt = "2024-12-15T10:45:00Z",
            lastEditedAt = "2024-12-15T10:45:00Z"
        ),
        NoteDomain(
            id = UUID.randomUUID().toString(),
            title = "Idea",
            content = "App idea: Voice note transcription",
            createdAt = "2025-06-18T09:00:00Z",
            lastEditedAt = "2025-06-18T09:00:00Z"
        ),
        NoteDomain(
            id = UUID.randomUUID().toString(),
            title = "Address",
            content = "123 Main St, Jakarta 12345",
            createdAt = "2023-08-22T16:30:00Z",
            lastEditedAt = "2023-08-22T16:30:00Z"
        ),

        // MEDIUM CONTENT
        NoteDomain(
            id = UUID.randomUUID().toString(),
            title = "Daily Journal",
            content = "Today was productive. Finished the project proposal and had a great meeting with the team. Looking forward to tomorrow's presentation.",
            createdAt = "2025-06-17T11:20:00Z",
            lastEditedAt = "2025-06-17T11:20:00Z"
        ),
        NoteDomain(
            id = UUID.randomUUID().toString(),
            title = "Recipe Ideas",
            content = "Nasi goreng ingredients: rice, eggs, soy sauce, chili, garlic, shallots. Cook rice first, then stir fry with aromatics and seasonings.",
            createdAt = "2025-06-16T13:45:00Z",
            lastEditedAt = "2025-06-16T13:45:00Z"
        ),
        NoteDomain(
            id = UUID.randomUUID().toString(),
            title = "Travel Plans",
            content = "Trip to Bali next month. Book flight by Friday. Hotel recommendations: Grand Hyatt, Mulia Resort. Activities: temple visits, beach day.",
            createdAt = "2024-11-08T15:30:00Z",
            lastEditedAt = "2024-11-08T15:30:00Z"
        ),
        NoteDomain(
            id = UUID.randomUUID().toString(),
            title = "Book Notes",
            content = "Atomic Habits chapter 3: Make it obvious, make it attractive, make it easy, make it satisfying. Great framework for building good habits.",
            createdAt = "2025-06-15T07:15:00Z",
            lastEditedAt = "2025-06-15T07:15:00Z"
        ),
        NoteDomain(
            id = UUID.randomUUID().toString(),
            title = "Workout Plan",
            content = "Monday: Chest and triceps. Tuesday: Back and biceps. Wednesday: Legs and glutes. Thursday: Shoulders and abs. Friday: Cardio and stretching.",
            createdAt = "2025-06-14T19:00:00Z",
            lastEditedAt = "2025-06-14T19:00:00Z"
        ),

        // LONG CONTENT
        NoteDomain(
            id = UUID.randomUUID().toString(),
            title = "Project Planning",
            content = "The new mobile application project requires careful planning and coordination between multiple teams. We need to establish clear timelines, define roles and responsibilities, and ensure proper communication channels are in place. The development team consists of 3 Android developers, 2 iOS developers, 1 backend engineer, and 1 UI/UX designer. Project timeline is 6 months with major milestones every 2 weeks. Key features include user authentication, data synchronization, offline mode, push notifications, and analytics integration. We also need to consider testing strategies, deployment processes, and maintenance procedures.",
            createdAt = "2025-06-13T12:30:00Z",
            lastEditedAt = "2025-06-13T12:30:00Z"
        ),
        NoteDomain(
            id = UUID.randomUUID().toString(),
            title = "Meeting Minutes",
            content = "Today's quarterly review meeting covered several important topics. First, we discussed the financial performance for Q2, which exceeded expectations by 15%. Sales team achieved 120% of their target, while marketing campaigns generated 40% more leads than projected. Second, we reviewed the product roadmap for the next quarter, including three major feature releases and two bug fix updates. Third, we addressed staffing needs with plans to hire 5 new engineers and 2 designers. Finally, we discussed the upcoming company retreat scheduled for August, which will focus on team building and strategic planning for the remainder of the year.",
            createdAt = "2025-06-12T16:45:00Z",
            lastEditedAt = "2025-06-12T16:45:00Z"
        ),
        NoteDomain(
            id = UUID.randomUUID().toString(),
            title = "Technical Documentation",
            content = "API Integration Guide: To integrate with our REST API, developers need to first obtain an API key from the developer portal. Authentication is handled through Bearer tokens with a 24-hour expiration. Rate limiting is enforced at 1000 requests per hour per API key. The base URL is https://api.example.com/v1/. Available endpoints include GET /users, POST /users, PUT /users/{id}, DELETE /users/{id}, GET /posts, POST /posts, and GET /analytics. Response format is JSON with standard HTTP status codes. Error handling includes detailed error messages and suggested solutions. SDK libraries are available for JavaScript, Python, Java, and Swift.",
            createdAt = "2024-09-03T14:20:00Z",
            lastEditedAt = "2024-09-03T14:20:00Z"
        ),
        NoteDomain(
            id = UUID.randomUUID().toString(),
            title = "Research Notes",
            content = "Machine learning model performance analysis shows promising results. The convolutional neural network achieved 94.7% accuracy on the test dataset, which is a 3.2% improvement over the previous baseline model. Training time was reduced by 25% through optimized hyperparameter tuning and batch processing. The model particularly excels at recognizing objects in low-light conditions and handling image rotations. Areas for improvement include better performance on edge cases and reducing false positive rates. Next steps involve expanding the training dataset with more diverse examples and implementing data augmentation techniques. Deployment timeline is set for next month pending security review and performance testing.",
            createdAt = "2025-06-11T10:15:00Z",
            lastEditedAt = "2025-06-11T10:15:00Z"
        ),
        NoteDomain(
            id = UUID.randomUUID().toString(),
            title = "Travel Journal",
            content = "Amazing trip to Yogyakarta last weekend! Started the day early with a visit to Borobudur temple at sunrise - absolutely breathtaking views and perfect lighting for photos. The historical significance and architectural details were fascinating. Spent the afternoon exploring Malioboro Street, trying local street food like gudeg, bakpia, and es dawet. The cultural diversity and friendly locals made the experience even more memorable. Visited the Sultan's Palace (Kraton) and learned about Javanese royal traditions. Evening was spent at Taman Sari water castle, which has beautiful ruins and underground passages. Definitely want to return for a longer visit to explore more temples and cultural sites.",
            createdAt = "2023-12-10T09:45:00Z",
            lastEditedAt = "2023-12-10T09:45:00Z"
        ),
        NoteDomain(
            id = UUID.randomUUID().toString(),
            title = "Learning Progress",
            content = "Kotlin development progress update: Completed advanced coroutines course and built a sample app with proper error handling and state management. Learned about Flow, StateFlow, and SharedFlow for reactive programming. Implemented MVVM architecture with ViewModels and LiveData. Explored Jetpack Compose for modern UI development - really impressed with the declarative approach and how it simplifies UI updates. Next topics to cover include dependency injection with Hilt, navigation component, and Room database integration. Planning to build a complete note-taking app to practice all these concepts together. Also considering learning Kotlin Multiplatform for cross-platform development.",
            createdAt = "2025-06-10T17:30:00Z",
            lastEditedAt = "2025-06-10T17:30:00Z"
        )
    )
}
