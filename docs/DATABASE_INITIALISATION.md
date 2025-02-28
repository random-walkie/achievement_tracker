# Achievement Tracker: Database initialisation Guide

## Overview

This document provides comprehensive guidance on initialising the Achievement Tracker database across different environments. Our goal is to create a flexible, secure, and developer-friendly approach to populating initial data.

## Initialisation Strategies

### Development Environment

#### Purpose
In the development environment, we use database initialisation to:
- Provide sample data for testing
- Create a consistent starting point for developers
- Demonstrate application functionality

#### Process
1. **Template Approach**
    - Use `data.sql.template` as a starting point
    - Developers create a local `src/main/resources/data.sql` file
    - Never commit sensitive or personal data to the repository

#### Configuration
Modify `application-dev.properties`:
```properties
# Enable SQL script-based initialisation
spring.sql.init.mode=always
spring.jpa.defer-datasource-initialisation=true
```

### Example Template (`data.sql.template`)
```sql
-- Sample Achievement initialisation
-- Copy this file to src/main/resources/data.sql and customize locally

INSERT INTO achievements (
    title, 
    description, 
    status, 
    date_started, 
    date_completed
) 
VALUES 
    ('Example Development Achievement', 
    'A sample achievement for local development', 
    'TODO', 
    CURRENT_DATE, 
    CURRENT_DATE
    );

INSERT INTO achievements_tags (
    achievement_id, 
    tags
)
VALUES (
    1, 'algorithm'
       );
```

## Best Practices

### Security Considerations
- Never include:
    - Real personal data
    - Sensitive information
    - Credentials
- Use generic, example-based data

### Environment-Specific Guidance

#### Local Development
1. Copy `data.sql.template` to `src/main/resources/data.sql`
2. Modify to suit your local testing needs
3. Ensure `src/main/resources/data.sql` is in `.gitignore`


#### Production
- Avoid automatic data initialisation
- Use database migration tools for controlled schema updates

## Troubleshooting

### Common Issues
1. **Data Not Inserting**
    - Verify `spring.sql.init.mode` is correctly set
    - Check database connection
    - Ensure SQL syntax matches your schema

## Mental Model

Think of database initialisation like preparing a new workspace:
- The template provides basic tools
- Each developer customizes their own setup
- The core structure remains consistent

## Recommended Workflow

1. Clone the repository
2. Copy `data.sql.template` to `src/main/resources/data.sql`
3. Customize local initialisation data
4. Ensure `src/main/resources/data.sql` is in `.gitignore`
5. Run application and verify initialisation