# Cher's Wardrobe (CPSC 210 Personal Project)

## Introduction

*Cher's Wardrobe* is an application that digitizes wardrobes
to make selecting an outfit more streamlined, personalized,
and convenient. This project aims to help users spend less
time and effort deciding what to wear, and more time actually
wearing what they like. In this way, *Cher's Wardrobe*
mitigates fast fashion practices and encourages a culture
around clothing where all items are well-liked, well-worn,
and here to stay.

## Using *Cher's Wardrobe*

Users will upload items of clothing to their digital wardrobe
with a title and several tags, including:
- Type, e.g, top, bottom, jacket, accessories
- Colour, e.g, blue, light grey, red
- Fit, e.g, tight, comfy, baggy
- Mood, e.g, sporty, preppy, flirty
- Dress code, e.g, casual, business casual, black tie

Once catalogued, **users can request outfits based on their
personal preference for the day, the occasion, or the weather.
For example, "over-sized, sporty outfit" will return the
clothing items that fit the bill**.

This application is intended for users of any demographic,
but tailored towards those that have a large collection
of clothes and want to downsize, organize, and stylize
more mindfully. **Teen and young adult users, in particular,
may benefit from being able to access all their clothes
more easily**.

## Motivation

As somewhat of a shopaholic that still paradoxically struggles
to find an outfit to wear, I think *Cher's Wardrobe* can help
narrow down fashion to a science. We can essentially eliminate
the guesswork and just state what mood or occasion we're dressing
for, and get the correct recommendations. Many of my peers, myself
included, have to see an article of clothing to consider
wearing it for the day, which often leads to neglecting an
article of clothing for weeks on end. This project is of course
inspired by [Cher's Wardrobe](https://www.youtube.com/watch?v=XNDubWJU0aU)
from the 1995 movie "Clueless", but I felt it was appropriate to keep
the name, given my actual name is Cheryl.

## User Stories
- As a user, I want to be able to add an item of clothing to my
digital wardrobe with a title and tag it with a type,
colour, fit, mood, and dress code.
- As a user, I want to be able to view all the items in my digital 
wardrobe in the order in which I added them.
- As a user, I want to be able to filter my wardrobe by colour, mood,
and dress code, such as "green, elegant, formal" or "purple, fun, cocktail"
and see a list of only the qualifying items.
- As a user, I want to be able to remove items from my wardrobe by either
entering the ID of the item (console) or clicking a trash icon (GUI).
- As a user, I want to be able to save my digital wardrobe, with all the
clothing items currently in it, to file (if I so choose).
- As a user, I want to be able to load in a previous digital
wardrobe from file (if I so choose).


## Instructions for Grader
- Upon running Main, you should be greeted by a page that says "Welcome
to your Digital Wardrobe!" From here...
- You can generate the first required action related to the user story
"adding multiple Xs to a Y" by clicking "Open Wardrobe" > "Add New Clothing Item".
Fill in the fields as required.
  - You will see the X (clothing item) you just added on display in the
appropriate panel.
- You can generate the second required action related to the user story
"adding multiple Xs to a Y" by clicking the small trash bin icon next to each
clothing item panel to remove the clothing item from your wardrobe.
- You can locate my visual component by admiring the little trash bins beside each
item, or by clicking "Main Menu" to return to the landing page, which contains an
image inspired by the early 2000s.
- You can save the state of my application (from the Main Menu) by clicking
"Open Wardrobe", then, or if you're already there, clicking "Save Wardrobe".
- You can reload the state of my application by clicking "Main Menu" (if you're
not already there), then "Load Wardrobe from File". Once you've loaded in the data,
just click "Open Wardrobe" once more to see the imported data.
- If you've imported my pre-configured data, you're also welcome to use the 
"Get Suggested Outfit" action to filter through clothing items. 
  - If you are using my data, then input "blue", "cute", and "Casual" in that
order when prompted for best results.
  - Otherwise, just be mindful to only input one of the following values for the
dress code field: Casual, Business Casual, Formal, Cocktail, White Tie.


## Phase 4: Task 2
Upon running my program, if I select "Open Wardrobe", then add two new items of
clothing (use "Add New Clothing Item" button on the side panel), then remove one (use the
trash bin icon), this is the event log that prints upon closing:
- Wed Nov 29 15:29:24 PST 2023
- Clothing item added.
- Wed Nov 29 15:29:29 PST 2023
- Clothing item added.
- Wed Nov 29 15:29:32 PST 2023
- Clothing item removed.

Beyond the two required actions, I also implemented a third action, filtering the
clothing items. In this phase, I added event logs for this, too. To view, select
"Get Suggested Outfit" and fill out accordingly. After filtering and upon closing, the
event log will read (regardless of whether you had any clothing items in the wardrobe
that matched your search criteria):
- Wed Nov 29 15:35:30 PST 2023
- Filtered wardrobe for search criteria.
- Wed Nov 29 15:35:30 PST 2023
- Filtered wardrobe for search criteria.
- Wed Nov 29 15:35:30 PST 2023
- Filtered wardrobe for search criteria.

Note that because we filter using three different criteria (colour, mood, and dress
code), any call to filter will have three event log statements. This is intentional at
this stage, but in the future, I can make the filtering functionality less strict.


## Phase 4: Task 3
Looking back now at my project in the form of a UML diagram, I feel that the structure
of the project is far too simple and there aren't enough classes to maintain the
Single Responsibility Principle. Especially regarding my GUI class. The first thing
that comes to mind as an improvement would be refactoring the WardrobeGUI class to
contain two associations, a MainMenu class and a WardrobeMenu class. This would shorten
my WardrobeGUI class significantly and also make it more readable. I found throughout the
process of making the GUI and debugging, I would often get lost between the methods and,
although they were aptly named, the sheer amount of them and the placement of them in
sequence was very vulnerable to human error. Aside from convenience and readability, this
would also improve functionality and runtime because at the moment, I have to initialize
all my pages at once, when I run the program. I just set the WardrobeMenu to be invisible
until I need it. This can make the program take a long time to start and also be
vulnerable to big changes like saving and loading from file, switching frequently 
between menus, etc. Given more time, I would abstract these two JPanels into two Java
classes and do all the initializing, styling, etc., in methods contained within these
new classes.

As we cover more design patterns in class, I'm also beginning to consider possibly
making some panels observers of the Wardrobe class. Currently, in my WardrobeGUI class,
I have four JPanel fields for the TopsPanel, BottomsPanel, JacketsPanel, and
AccessoriesPanel. They are updated, revalidated, and repainted, every time a clothing
item is added or removed. And you can see, there are many methods dedicated to
executing this update, including removeItem(), removeTop(), removeBottom(), removeJacket(),
removeAccessories(), displayLoadedTops(), displayLoadedBottoms(), 
displayLoadedJackets(), displayLoadedAccessories(), etc. As you can tell from the names,
and if you viewed the method bodies, there is a lot of duplication here, essentially
performing the same tasks for different types of clothing. I think in the future I could
abstract the TopsPanel, BottomsPanel, JacketsPanel, and AccessoriesPanel to be observers
of the Wardrobe class so that they update when clothing is added or removed without so
much duplication and to be less bug-vulnerable.
