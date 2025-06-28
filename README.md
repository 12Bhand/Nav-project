
![image](https://github.com/user-attachments/assets/b12ea3a4-7b4e-4db5-8d9b-9cfc3d21a883)

1. **Header Section**
   - Application title ("Nav Calculator")
   - Tagline ("Professional Stock NAV Analysis Tool")

2. **Stock Information Panel**
   - Stock Symbol input field
   - Buying Price field (pre-populated with ¥100.00 example)
   - Selling Price field (pre-populated with ¥120.00 example)

3. **Calculation Section**
   - "Calculate NAV" action button
   - Current NAV display (showing ¥0.00 in ready state)

4. **Navigation Menu**
   - Home button
   - History button
   - Settings button
   - Clear button

## How to Run the Application

### Method 1: Using Pre-built JAR File
1. Ensure you have Java installed (JDK 8 or later)
2. Download the latest `TataNavCalculator.jar` file
3. Run the application using either:
   - Double-click the JAR file (if Java is properly configured), or
   - Command line: `java -jar TataNavCalculator.jar`

### Method 2: Running from Source Code
1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/TataNavCalculator.git
   ```
2. Navigate to project directory:
   ```bash
   cd TataNavCalculator
   ```
3. Compile the source:
   ```bash
   javac -d bin src/com/tatanav/*.java
   ```
4. Run the application:
   ```bash
   java -cp bin com.tatanav.Main
   ```

## System Requirements
- **Operating System**: Windows 10/11, macOS 10.15+, or Linux
- **Java Version**: JDK 8 or later
- **Memory**: Minimum 1GB RAM (2GB recommended)
- **Display**: 1024×768 resolution minimum

## Troubleshooting
If you encounter issues:
1. Verify Java installation: `java -version`
2. Check file permissions on the JAR file
3. Ensure no other Java applications are conflicting on port
4. For GUI issues, try running with: `java -Dsun.java2d.uiScale=1 -jar TataNavCalculator.jar`

## Application Workflow
1. Launch the application
2. Enter stock details:
   - Stock symbol (optional)
   - Buying price
   - Selling price
3. Click "Calculate NAV"
4. View results in the display panel
5. Use navigation buttons to access other features

Note: The workflow diagram (workflow.png) should show:
1. Launch screen
2. Data entry screen
3. Calculation process
4. Results display
5. Navigation options
