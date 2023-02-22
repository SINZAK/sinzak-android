# Sinzak Android

## Spec Sheet

<p>
<img src="https://img.shields.io/badge/Core--KTX-1.7.0-green">
    <img src="https://img.shields.io/badge/Retrofit-2.9.0-red">
    <img src="https://img.shields.io/badge/OkHttp-4.9.3-blue">
    <img src="https://img.shields.io/badge/KTX-2.5.1-yellow">
    <img src="https://img.shields.io/badge/HiltAndroid-2.44-orange">
</p>

## Description

Sinzak Android is an online outsourcing and art market platform for students in art college. This project uses the MVVM pattern, where the View observes the ViewModel, the ViewModel observes the Storage, and the Storage requests data fetch from the RemoteSource created by Retrofit. The MainActivity has several fragments and a Navigation class, which holds information about page stacks. The MainActivity reads the value of the page stack and shows the top page fragment.

## Directory Structure
```mermaid
graph TD
H[model] --> I[certify]
H --> J[chat]
H --> K[context]
H --> L[insets]
H --> M[market]
H --> N[navigate]
H --> O[profile]
H --> P[works]
Q[remote] --> R[dataclass]
R --> S[chat]
R --> T[history]
R --> U[local]
R --> V[product]
R --> W[profile]
R --> X[request]
X --> Y[certify]
X --> Z[login]
X --> AA[market]
X --> AB[profile]
R --> AC[response]
AC --> AD[certify]
AC --> AE[history]
AC --> AF[home]
AC --> AG[login]
AC --> AH[market]
AC --> AI[profile]
Q --> AJ[remotesources]
Q --> AK[retrofit]
AL --> AM[social]
```


```mermaid
graph TD
AN[ui] --> AO[base]
AN --> AP[login]
AP --> AQ[agreement]
AP --> AR[cert]
AP --> AS[email]
AP --> AT[interest]
AP --> AU[name]
AP --> AV[welcome]
AN --> AW[main]
AW --> AX[chat]
AX --> AY[viewmodel]
AW --> AZ[home]
AZ --> BA[adapter]
AZ --> BB[more]
AZ --> BC[notification]
BC --> BD[adapter]
AZ --> BE[viewmodel]
AW --> BF[market]
BF --> BG[adapter]
BF --> BH[artdetail]
BH --> BI[dialog]
BH --> BJ[suggest]
BF --> BK[viewmodel]
AW --> BL[outsourcing]
BL --> BM[viewmodel]
AW --> BN[postwrite]
BN --> BO[adapter]
BN --> BP[fragment]
BN --> BQ[viewmodels]
AW --> BR[profile]
BR --> BS[art]
BS --> BT[adapter]
BR --> BU[certification]
BU --> BV[adapter]
BR --> BW[edit]
BR --> BX[follow]
BX --> BY[adapter]
BR --> BZ[report]
BR --> CA[scrap]
CA --> CB[adapter]
BR --> CC[setting]
BR --> CD[viewmodel]
AW --> CE[search]

```


## Technologies Used

Core-KTX: 1.7.0
Retrofit: 2.9.0
OkHttp: 4.9.3
KTX: 2.5.1
Hilt-Android: 2.44
License
This project is licensed under the MIT License - see the LICENSE file for details.

## Contributing

Contributions are always welcome! If you'd like to contribute, please fork the repository and create a pull request.
