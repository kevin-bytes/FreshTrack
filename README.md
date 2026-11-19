# FreshTrack: Farm Produce Cold-Chain Tracker

**See how much produce a bad journey costs, before you make it.**

FreshTrack is a Java and JavaFX simulation of how fresh produce spoils on its way from a farm to a market in Ghana. You create a batch (for example, tomatoes from Techiman), choose a truck and a storage option, and run the journey hour by hour. FreshTrack shows how fresh the batch is at each stage and how much of it is lost. Then you run the same journey with a refrigerated truck and compare the two results side by side.

Final project for Ashesi University, built by Group 8.

---

## Why we built it

A large share of fresh produce in Ghana is lost before it reaches the buyer. Heat, long travel times, open trucks and poor storage all speed up spoilage, and farmers and traders usually learn this only after the loss has happened. FreshTrack lets someone ask simple questions before a trip:

- How long will these tomatoes last on an open truck in the afternoon?
- What changes if we use a cold room at the market?

## Features

- **Four produce types:** tomato, yam, maize, mango, each with its own spoilage behaviour
- **Two transport types:** open truck, refrigerated truck
- **Two storage types:** warehouse, cold room
- **Hourly simulation** that records temperature, freshness and kilograms lost
- **Side-by-side comparison** of two setups for the same batch
- **CSV export** of results and import of saved scenarios
- **JavaFX interface** with setup, simulation, comparison and results screens

## How it works

A **Batch** holds a produce type, a quantity in kg, and a freshness value from 100 down to 0. A **Journey** is a list of legs, for example: farm storage (2 h) → truck (6 h) → market storage (12 h).

For every hour of every leg, the transport or storage object reports the current temperature, and the `SpoilageModel` works out the freshness loss:

```
freshness loss per hour = base rate of the produce × temperature factor
```

The temperature factor rises as the temperature climbs above the produce's ideal range. When freshness reaches a set limit, part of the batch counts as lost.

## Design

| Concept | Where it appears |
|---|---|
| Inheritance | `Produce`, `Transport` and `Storage` are abstract classes with concrete subclasses |
| Interface | `SpoilageModel` is implemented by `LinearSpoilageModel` |
| Polymorphism | Each produce, transport and storage type overrides its own spoilage rate or temperature rule |
| Composition | A `Journey` has many `JourneyLeg`s; each leg has a `Transport` or `Storage` |
| Collections | `HashMap<Batch, List<Condition>>` for results, `ArrayList<JourneyLeg>` for journeys |
| File I/O | `ResultsExporter` reads and writes CSV |

## Data and accuracy

Shelf-life and temperature values are **approximate**. Real shelf-life data for Ghanaian routes is hard to find, so we use approximate values from published guides, keep them in one place, and list their sources. FreshTrack is a learning and demonstration tool, not a scientific one.

## Scope

FreshTrack uses a simple time-and-temperature spoilage formula. It does **not** model humidity, bruising or ethylene, does **not** use live weather or GPS data, and leaves out random events such as truck breakdowns until the core simulation is stable.

## Team

| Member | Focus |
|---|---|
| Crista | Produce hierarchy, base spoilage rates, spoilage models |
| Stephen Mensah | Transport and Storage temperature rules, shelf-life data and sources |
| Abdoul-Latif | JavaFX interface |
| Kevin Einstein | Repository, UML diagrams, CSV import and export |

Journey, Simulation and testing are shared. Everyone writes logic code.

## Contributing

- Small commits with clear messages.
- No direct pushes to `main`. Open a pull request and get one review from another member before merging.

## Roadmap

| Week | Goal |
|---|---|
| 1 | Core model and console version |
| 2 | Journey and simulation logic, custom exceptions, first tests |
| 3 | JavaFX interface connected to the logic |
| 4 | CSV import and export, polish, final UML, demo |
