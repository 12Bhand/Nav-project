# Tata Nav Calculator Documentation

## Overview
The Tata Nav Calculator is a professional Java Swing desktop application designed for stock market analysis with a focus on Net Asset Value (NAV) calculations. It provides traders, investors, and financial analysts with powerful tools to evaluate stock performance.

## Key Features

### Stock NAV Calculation
- Calculate Net Asset Value based on buying and selling prices
- Real-time computation with immediate results
- Support for multiple stock symbols

### Visualization Tools
- Dynamic line charts showing NAV trends
- Graphical representation of profit/loss percentages
- Color-coded indicators for positive/negative returns

### Navigation System
- Multi-panel interface for different functionalities:
  - Home (main calculation screen)
  - History (past calculations)
  - Settings (application preferences)
- Clear button to reset current inputs

### Data Management
- Track and display calculation history
- Export functionality for reports and data analysis
- Local storage of user preferences

## Target Users
- Stock traders and investors
- Financial analysts and portfolio managers
- Students learning financial calculations
- Individual investors interested in stock market analysis

## Installation Guide

### Prerequisites
- **Java Development Kit (JDK):** Version 8 or higher
- **Operating System:** Windows, macOS, or Linux
- **Memory:** Minimum 512MB RAM (1GB recommended)
- **Storage:** 50MB free disk space

### Installation Methods

#### Method 1: Source Code Compilation
1. Download the source code from the repository
2. Create project directory:
   ```bash
   mkdir TataNavCalculator
   cd TataNavCalculator
   ```
3. Compile the Java source files
4. Run the application using:
   ```bash
   java -jar TataNavCalculator.jar
   ```

#### Method 2: Pre-built Package (Recommended)
1. Download the latest release package
2. Double-click the installer or JAR file
3. Follow the on-screen instructions

## User Interface Overview

![Application Screenshot](image.png)

The interface includes:
1. **Stock Information Section**:
   - Stock symbol input
   - Buying price field (default ¥100.00 shown)
   - Selling price field (default ¥120.00 shown)

2. **Calculation Results**:
   - "Calculate NAV" button to perform computation
   - Current NAV display (¥0.00 when ready for calculation)

3. **Navigation Panel**:
   - Home button
   - History button
   - Settings button
   - Clear button to reset inputs

## Usage Instructions
1. Enter the stock symbol (optional)
2. Input the buying price in the designated field
3. Input the selling price in the designated field
4. Click "Calculate NAV" to see results
5. View historical calculations in the History section
6. Adjust application preferences in Settings

## Technical Specifications
- **Programming Language:** Java
- **GUI Framework:** Swing
- **Data Persistence:** Local file storage
- **Charting Library:** JFreeChart (or equivalent)
